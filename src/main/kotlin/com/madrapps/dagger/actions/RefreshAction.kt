package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.roots.OrderEnumerator
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
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
        Presenter.reset()
        val lo = measureTimeMillis {
            val project = e.project ?: throw IllegalArgumentException()

            val validModules = project.allModules().filter { it.sourceRoots.isNotEmpty() }

            validModules.forEach { module ->
                println("Module - $module")
                val sources1 =
                    module.sourceRoots.filter { it.path.endsWith("main/java") || it.path.endsWith("main/kotlin") }
                println("Sources - $sources1")
                if (sources1.isNotEmpty()) {

                    val compiler = ToolProvider.getSystemJavaCompiler()
                    val diagnostics = DiagnosticCollector<JavaFileObject>()
                    val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(diagnostics, null, null)

                    val outputDirectory = File(module.externalProjectPath, "build/dagger-plugin")
                    outputDirectory.mkdirs()

                    fileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(outputDirectory))


                    val files: MutableList<File> =
                        OrderEnumerator.orderEntries(module).recursively().pathsList.virtualFiles
                            .filterNot { it.path.contains("com.google.dagger") }
                            .map { File(it.path) }.toMutableList()
                    val jarPathForClass = PathUtil.getJarPathForClass(DaggerCollections::class.java)
                    files.add(File(jarPathForClass))
                    val kotlinClasses = File(module.externalProjectPath, "build/tmp/kotlin-classes/debug")
                    if (kotlinClasses.exists()) {
                        files.add(kotlinClasses)
                    }
                    files.toMutableList().forEach {
                        val path = it.path
                        if (path.endsWith("build/intermediates/javac/debug/classes")) {
                            val newPath = path.replace(
                                "build/intermediates/javac/debug/classes",
                                "build/tmp/kotlin-classes/debug"
                            )
                            val cl = File(newPath)
                            if (cl.exists()) {
                                files.add(cl)
                            }
                        }
                    }

                    fileManager.setLocation(StandardLocation.CLASS_PATH, files)

                    val psiFiles = mutableListOf<PsiFile>()
                    sources1.forEach {
                        File(it.path).walkTopDown()
                            .filter { it.isFile && (it.extension == "java" || it.extension == "kt") }
                            .forEach {
                                val vf = LocalFileSystem.getInstance().findFileByIoFile(it)
                                if (vf != null) {
                                    PsiManager.getInstance(project).findFile(vf)?.let { psiFiles += it }
                                }
                            }
                    }

                    val classes = psiFiles.map(PsiFile::toUElement).filterIsInstance<UFile>().flatMap(UFile::classes)
                        .mapNotNull { it.qualifiedName }

                    if (classes.isNotEmpty()) {
                        val task = compiler.getTask(null, fileManager, diagnostics, null, classes, null)
                        task.setProcessors(listOf(ComponentProcessor.forTesting(SpiPlugin()), AndroidProcessor()))
                        val success = task.call()

                        for (diagnostic in diagnostics.diagnostics) {
                            System.err.println(diagnostic)
                        }
                    }
                }
            }
        }
        println(" Time Taken - $lo")
    }
}