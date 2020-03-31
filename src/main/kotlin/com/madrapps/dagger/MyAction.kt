package com.madrapps.dagger

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.roots.OrderEnumerator
import com.intellij.util.PathUtil
import dagger.internal.DaggerCollections
import dagger.internal.codegen.ComponentProcessor
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.idea.util.sourceRoots
import java.io.File
import javax.tools.*
import javax.tools.JavaCompiler.CompilationTask
import kotlin.system.measureTimeMillis


class MyAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val lo = measureTimeMillis {
            println("TTRRTT, ActionPerformed")

            val project = e.project ?: throw IllegalArgumentException()
            val sourceFolder = project.allModules().flatMap { it.sourceRoots.toList() }
                .filter {
                    it.canonicalPath?.endsWith("src/main/java") == true
                    // || it.canonicalPath?.endsWith("src/main/kotlin") == true
                }
            println(sourceFolder)


            val compiler = ToolProvider.getSystemJavaCompiler()
            val diagnostics = DiagnosticCollector<JavaFileObject>()
            val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(diagnostics, null, null)
            val outputDirectory = File(project.basePath, "build/dagger-plugin")
            outputDirectory.mkdirs()

            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(outputDirectory))
            println("--- | " + fileManager.getLocation(StandardLocation.CLASS_OUTPUT).toList())

            val mutableListOf = mutableListOf<JavaFileObject>()
            val javaObjects =
                File(sourceFolder.first().canonicalPath).walkTopDown().filter { it.isFile && it.extension == "java" }
                    .forEach {
                        mutableListOf += fileManager.getJavaFileObjects(it)
                    }

            val modules = project.allModules().filter { it.sourceRoots.isNotEmpty() }

            val module = project.allModules().get(1)
            val files: MutableList<File> = OrderEnumerator.orderEntries(module).recursively().pathsList.virtualFiles
                .filterNot { it.path.contains("com.google.dagger") }
                .map { File(it.path) }.toMutableList()

            val jarPathForClass = PathUtil.getJarPathForClass(DaggerCollections::class.java)

            files.add(File(jarPathForClass))
            fileManager.setLocation(StandardLocation.CLASS_PATH, files)
            val classRoots = OrderEnumerator.orderEntries(module).classesRoots.filter { it.path.endsWith("main") }
                .map { File(it.path) }
            val classes = mutableListOf<String>()
            classRoots.forEach {
                it.walkTopDown().forEach {
                    classes += it.path
                }
            }

            val map = mutableListOf.map {
                it.name
            }

            val task: CompilationTask =
                compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    null,
                    null,
                    mutableListOf
                )
            val specPlugin = SpewPlugin()
            val forTesting = ComponentProcessor.forTesting(specPlugin)
            task.setProcessors(
                listOf(forTesting)
            )
            val success = task.call()
            for (diagnostic in diagnostics.diagnostics) {
                // System.err.println(diagnostic)
            }
        }
        println(" LO $lo")
    }
}