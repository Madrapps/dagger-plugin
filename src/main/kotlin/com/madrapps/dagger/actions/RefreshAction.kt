package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.module.Module
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task.Backgroundable
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.OrderEnumerator
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.tasks.TaskManager
import com.intellij.util.PathUtil
import com.madrapps.dagger.Presenter
import com.madrapps.dagger.SpiPlugin
import dagger.android.processor.AndroidProcessor
import dagger.internal.DaggerCollections
import dagger.internal.codegen.ComponentProcessor
import org.jetbrains.kotlin.idea.configuration.externalProjectPath
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.idea.util.sourceRoots
import org.jetbrains.uast.UFile
import org.jetbrains.uast.toUElement
import java.io.File
import javax.tools.*
import kotlin.system.measureTimeMillis

class RefreshAction : AnAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.RefreshAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("TTRRTT, ActionPerformed")
        val project = e.project ?: throw IllegalArgumentException()
        Presenter.reset()

        val validModules = project.allModules().filter { it.sourceRoots.isNotEmpty() }
        val map = validModules.map { module ->
            val sources = module.sourceRoots.filter {
                it.path.endsWith("main/java") || it.path.endsWith("main/kotlin")
            }
            val classes = getClasses(sources, project)
            val outputDirectory = getClassOutput(module)
            val classpath: MutableList<File> = getClasspath(module)
            Triple(classes, classpath, outputDirectory)
        }.filter { it.first.isNotEmpty() }


        val taskManager = project.getComponent(TaskManager::class.java)
        if (taskManager != null) {
            val task: Backgroundable =
                object : Backgroundable(project, "Analysing dagger dependencies", false) {

                    override fun run(indicator: ProgressIndicator) {
                        val lo = measureTimeMillis {
                            map.forEach { (classes, classpath, output) ->
                                compile(classes, classpath, output)
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

    private fun compile(classes: List<String>, classpath: MutableList<File>, outputDirectory: File) {
        val compiler = ToolProvider.getSystemJavaCompiler()
        val diagnostics = DiagnosticCollector<JavaFileObject>()
        val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(diagnostics, null, null)
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(outputDirectory))
        fileManager.setLocation(StandardLocation.CLASS_PATH, classpath)

        val task = compiler.getTask(null, fileManager, diagnostics, null, classes, null)
        task.setProcessors(listOf(ComponentProcessor.forTesting(SpiPlugin()), AndroidProcessor()))
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
        val kotlinClasses = File(module.externalProjectPath, "build/tmp/kotlin-classes")
        if (kotlinClasses.exists()) {
            kotlinClasses.listFiles()?.forEach {
                files.add(it)
            }
        }
        files.toMutableList().forEach {
            val path = it.path
            if (path.contains("build/intermediates/javac")) {
                val newPath = path.substringBeforeLast("build/intermediates/javac") + "build/tmp/kotlin-classes"
                val cl = File(newPath)
                if (cl.exists()) {
                    cl.listFiles()?.forEach {
                        files.add(it)
                    }
                }
            }
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
}