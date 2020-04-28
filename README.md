# <img src="/preview/logo.png" title="logo" height="80" width="80" /> Dagger Plugin
IntelliJ iDEA plugin to work with projects using <a href="https://dagger.dev/">Dagger 2</a> library

<img src="/preview/screenshare.gif" alt="preview" title="preview"/>

Install
-----
You can install the plugin from `Preferences` -> `Plugins` and search for the plugin. You can also download the plugin from the <a href="https://plugins.jetbrains.com/plugin/14211-dagger">intelliJ iDEA Marketplace</a>.

Features
-----
- View the dependency graph in tree format
- Jump to source code from the graph
- View parents and children of a node in the graph
- Fully supported for project using both Java and Kotlin including Android

Usage
-----
- Build your project, and the graph will be displayed in the <b>Dagger</b> tool window
- Tap on <b>Refresh</b> in the Dagger tool window to refresh the graph
- The plugin itself uses Dagger 2.27 and hence validations and graph will be calculated based on this version. For optimal usage, consider updating Dagger to the latest version
