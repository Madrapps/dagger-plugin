package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.module.Module
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task.Backgroundable
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.OrderEnumerator
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.tasks.TaskManager
import com.intellij.util.PathUtil
import com.madrapps.dagger.SpiPlugin
import com.madrapps.dagger.getCompilerOutputFile
import com.madrapps.dagger.getKotlinOutputDir
import com.madrapps.dagger.services.service
import com.sun.tools.javac.api.JavacTool
import dagger.android.processor.AndroidProcessor
import dagger.internal.DaggerCollections
import dagger.internal.codegen.ComponentProcessor
import org.jetbrains.kotlin.idea.configuration.externalProjectPath
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.idea.util.sourceRoots
import org.jetbrains.uast.UFile
import org.jetbrains.uast.toUElement
import java.io.File
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.StandardJavaFileManager
import javax.tools.StandardLocation
import kotlin.system.measureTimeMillis

class RefreshAction : AnAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.RefreshAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("TTRRTT, ActionPerformed")
        val project = e.project ?: throw IllegalArgumentException()
        project.service.reset()

        val validModules = project.allModules().filter { it.sourceRoots.isNotEmpty() }
        val map = validModules.map { module ->
            val sources = module.sourceRoots.filter {
                it.path.endsWith("main/java") || it.path.endsWith("main/kotlin")
            }
            val cu = getCompilationUnits(sources)
            val classes = getClasses(sources, project)
            val outputDirectory = getClassOutput(module)
            val classpath: MutableList<File> = getClasspath(module)
            Bundle(classes, classpath, outputDirectory, cu)
        }.filter { it.classes.isNotEmpty() }

        val taskManager = project.getComponent(TaskManager::class.java)
        if (taskManager != null) {
            val task: Backgroundable =
                object : Backgroundable(project, "Analysing dagger dependencies", false) {

                    override fun run(indicator: ProgressIndicator) {
                        val lo = measureTimeMillis {
                            map.forEach { (classes, classpath, output, cu) ->
                                try {
                                    compile(classes, classpath, output, project, cu)
                                } catch (e: Throwable) {
                                    println("Exception Handled")
                                    e.printStackTrace()
                                }
                            }
                        }
                        println(" Time Taken - $lo")
                    }

                    override fun onSuccess() {

                    }
                }
            val indicator = BackgroundableProcessIndicator(task)
            ProgressManager.getInstance().runProcessWithProgressAsynchronously(task, indicator)
        }
    }

    private fun compile(
        classes: List<String>,
        classpath: MutableList<File>,
        outputDirectory: File,
        project: Project,
        cu: List<File>
    ) {
        val compiler = JavacTool.create()
        val diagnostics = DiagnosticCollector<JavaFileObject>()
        val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(diagnostics, null, null)
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(outputDirectory))
        fileManager.setLocation(StandardLocation.CLASS_PATH, classpath)

        val units = cu.flatMapTo(mutableListOf()) {
            fileManager.getJavaFileObjects(it)
        }

        val task = compiler.getTask(null, fileManager, diagnostics, null, classes, null)
        task.setProcessors(listOf(ComponentProcessor.forTesting(SpiPlugin(project)), AndroidProcessor()))
        task.call()

        for (diagnostic in diagnostics.diagnostics) {
            System.err.println(diagnostic)
        }
    }

    private fun getClassOutput(module: Module): File {
        val outputDirectory = File(module.externalProjectPath, "build/dagger-plugin")
        outputDirectory.mkdirs()
        return outputDirectory
    }

    private fun getClasspath(module: Module): MutableList<File> {
        val files: MutableList<File> =
            OrderEnumerator.orderEntries(module).recursively().pathsList.virtualFiles
                .filterNot { it.path.contains("com.google.dagger") }
                .map { File(it.path) }.toMutableList()
        val jarPathForClass = PathUtil.getJarPathForClass(DaggerCollections::class.java)
        files.add(File(jarPathForClass))

        module.getKotlinOutputDir()?.let(files::add)
        module.getCompilerOutputFile()?.let(files::add)

        ModuleRootManager.getInstance(module).dependencies.forEach {
            it.getKotlinOutputDir()?.let(files::add)
            it.getCompilerOutputFile()?.let(files::add)
        }
        return files
    }

    private fun getClasses(
        sources: List<VirtualFile>,
        project: Project
    ): List<String> {
        val psiFiles = mutableListOf<PsiFile>()
        sources.forEach {
            File(it.path).walkTopDown()
                .filter { it.isFile && (it.extension == "java" || it.extension == "kt") }
                .forEach {
                    val vf = LocalFileSystem.getInstance().findFileByIoFile(it)
                    if (vf != null) {
                        PsiManager.getInstance(project).findFile(vf)?.let { psiFiles += it }
                    }
                }
        }
        return psiFiles.map(PsiFile::toUElement).filterIsInstance<UFile>().flatMap(UFile::classes)
            .mapNotNull { it.qualifiedName }
    }

    private fun getCompilationUnits(
        sources: List<VirtualFile>
    ): List<File> {
        return sources.flatMap {
            File(it.path).walkTopDown()
                .filter { it.isFile && (it.extension == "java") }.toList()
        }
    }
}

data class Bundle(
    val classes: List<String>,
    val classpath: MutableList<File>,
    val outputDirectory: File,
    val cu: List<File>
)