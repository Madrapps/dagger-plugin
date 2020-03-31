package com.madrapps.dagger

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.roots.OrderEnumerator
import com.intellij.util.PathUtil
import com.sun.tools.javac.api.JavacTool
import com.sun.tools.javac.resources.compiler
import dagger.internal.DaggerCollections
import dagger.internal.codegen.ComponentProcessor
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.idea.util.sourceRoots
import java.io.File
import java.util.*
import javax.tools.*
import javax.tools.JavaCompiler.CompilationTask


class MyAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
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
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(File(project.basePath, "build/dagger-plugin")))
        println("--- | " + fileManager.getLocation(StandardLocation.CLASS_OUTPUT).toList())

        val mutableListOf = mutableListOf<JavaFileObject>()
        val javaObjects = File(sourceFolder.first().canonicalPath).walkTopDown().filter { it.isFile && it.extension =="java" }.forEach {
            mutableListOf += fileManager.getJavaFileObjects(it)
        }
        val module = project.allModules().get(1)
        val files: MutableList<File> = OrderEnumerator.orderEntries(module).recursively().pathsList.virtualFiles
            .filterNot { it.path.contains("com.google.dagger") }
            .map { File(it.path) }.toMutableList()

        val jarPathForClass = PathUtil.getJarPathForClass(DaggerCollections::class.java)
        files.add(File(jarPathForClass))
        fileManager.setLocation(StandardLocation.CLASS_PATH, files)
        val classRoots = OrderEnumerator.orderEntries(module).classesRoots.filter { it.path.endsWith("main") }.map { File(it.path) }
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
            compiler.getTask(null, fileManager, diagnostics, null, listOf("com.madrapps.dagger.component.EmptyComponent"), null)
        val componentProcessor = ComponentProcessor()
        task.setProcessors(
            listOf(componentProcessor)
        )
        val success = task.call()
        for (diagnostic in diagnostics.diagnostics) {
            System.err.println(diagnostic)
        }
    }
}