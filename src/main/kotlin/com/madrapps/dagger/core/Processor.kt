package com.madrapps.dagger.core

import com.intellij.openapi.module.Module
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
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
import com.madrapps.dagger.utils.getCompilerOutputFile
import com.madrapps.dagger.utils.getKotlinOutputDir
import com.madrapps.dagger.services.log
import dagger.android.processor.AndroidProcessor
import dagger.internal.DaggerCollections
import dagger.internal.codegen.ComponentProcessor
import org.jetbrains.kotlin.idea.configuration.externalProjectPath
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.idea.util.sourceRoots
import org.jetbrains.uast.UFile
import org.jetbrains.uast.toUElement
import java.io.File
import java.util.*
import javax.tools.*
import kotlin.system.measureTimeMillis

class Processor {

    private lateinit var spiPlugin: SpiPlugin
    private var running = false

    fun isRunning(): Boolean = running

    fun process(project: Project) {
        spiPlugin = SpiPlugin(project)
        project.log("Processor - Process Started in Thread ${Thread.currentThread()}")

        val validModules = project.allModules().filter { it.sourceRoots.isNotEmpty() }
        project.log("Processor - modules:${validModules.size}")
        val map = validModules.map { module ->
            project.log("Processor - Fetching module:${module}")
            val sources = module.sourceRoots.filter {
                it.path.endsWith("main/java") || it.path.endsWith("main/kotlin")
            }
            val classes = getClasses(sources, project)
            val outputDirectory = getClassOutput(module)
            val classpath: MutableList<File> = getClasspath(module)
            project.log("Processor - Fetched module:${module}")
            Bundle(classes, classpath, outputDirectory)
        }.filter { it.classes.isNotEmpty() }
        project.log("Processor - Bundle:${map}")
        val taskManager = project.getService(TaskManager::class.java)
        project.log("Processor - taskManager:${taskManager}")
        if (taskManager != null) {
            val task: Task.Backgroundable =
                object : Task.Backgroundable(project, "Analysing dagger dependencies", false) {

                    override fun run(indicator: ProgressIndicator) {
                        running = true
                        project.log("Processor - Running background task")
                        val lo = measureTimeMillis {
                            map.forEach { (classes, classpath, output) ->
                                try {
                                    compile(classes, classpath, output, project)
                                } catch (e: Throwable) {
                                    running = false
                                    project.log("Exception Handled")
                                    e.printStackTrace()
                                }
                            }
                        }
                        project.log("Finish background task - $lo")
                        running = false
                    }

                    override fun onFinished() {
                        running = false
                    }
                }
            val indicator = BackgroundableProcessIndicator(task)
            ProgressManager.getInstance().runProcessWithProgressAsynchronously(task, indicator)
            project.log("Processor - Start background task")
        }
    }

    private fun compile(
        classes: List<String>,
        classpath: MutableList<File>,
        outputDirectory: File,
        project: Project
    ) {
        val compiler = ToolProvider.getSystemJavaCompiler()
        val diagnostics = DiagnosticCollector<JavaFileObject>()
        val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(diagnostics, null, null)
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(outputDirectory))
        fileManager.setLocation(StandardLocation.CLASS_PATH, classpath)

        project.log("Processor - Compiling .[${classes.size}].[${classpath.size}].")
        val task = compiler.getTask(null, fileManager, diagnostics, null, classes, null)
        task.setProcessors(listOf(ComponentProcessor.forTesting(spiPlugin), AndroidProcessor()))
        val success = task.call()
        project.log("Processor - Compile success? $success")
        for (diagnostic in diagnostics.diagnostics) {
            System.err.println(diagnostic)
            val error = diagnostic.getMessage(Locale.US)
            project.log("Processor - Compile error - $error")
        }
    }

    private fun getClassOutput(module: Module): File {
        module.project.log("Processor - getClassOutput:${module}")
        val outputDirectory = File(module.externalProjectPath, "build/dagger-plugin")
        outputDirectory.mkdirs()
        return outputDirectory
    }

    private fun getClasspath(module: Module): MutableList<File> {
        module.project.log("Processor - getClasspath:${module}")
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
        project.log("Processor - getClasses")
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

data class Bundle(
    val classes: List<String>,
    val classpath: MutableList<File>,
    val outputDirectory: File
)