# <img src="/preview/logo.png" title="logo" height="80" width="80" /> Dagger Plugin

IntelliJ iDEA plugin to work with <b>Java/Kotlin</b> projects using <a href="https://dagger.dev/">Dagger 2</a> library

<img src="/preview/screenshare.gif" alt="preview" title="preview"/>

Install
-----
You can install the plugin from `Preferences` -> `Plugins` and search for the plugin. You can also download the plugin from the <a href="https://plugins.jetbrains.com/plugin/14211-dagger">intelliJ iDEA Marketplace</a>.

Features
-----
- View the dependency graph in tree format
- Jump to source code from the graph
- View parents and children of a node in the graph
- View basic validation errors directly in the editor
- Fully supported for project using both Java and Kotlin including Android

Usage
-----
- Build your project, and the graph will be displayed in the <b>Dagger</b> tool window
- Tap on <b>Refresh</b> in the Dagger tool window to refresh the graph
- The plugin itself uses Dagger 2.31 and hence validations and graph will be calculated based on this version. For optimal usage, consider updating Dagger to the latest version

Testing
-----
All validation logic are unit tested. The test data is organised in the following structure:
```$xslt
src
  |_test
    |_testData
      |_component
        |_ ... (directory for each error highlight)
      |_inject
        |_ ... (directory for each error highlight)
```

For each **error** highlight multiple test cases are written. You will notice that there are no test cases under
`test/kotlin/com.madrapps.dagger`. For each annotation there will be a KT test file annotated with `GenerateTest`.
The test cases (classes and methods) are created at compile time by means of the annotation processor:
<a href="https://github.com/thsaravana/dagger-plugin-processor">`dagger-plugin-processor`</a>. The above testData
directory structure is crucial for the processor to work properly.

For more clarity, take a look at [`src/test/kotlin/com.madrapps.dagger.InjectTestCase.kt`](https://github.com/Madrapps/dagger-plugin/blob/master/src/test/kotlin/com/madrapps/dagger/InjectTestCase.kt)
