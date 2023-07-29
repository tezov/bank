plugins {
    id("com.tezov.plugin_project.catalog")
}

tezovCatalog {
//    verboseCatalogBuild = true
//    verbosePluginApply = true
//    verboseReadValue = true
//    verboseCheckDependenciesVersion = true
    jsonFile = jsonFromFile("F:/android_project/tezov_banque/tezov_bank.catalog.json")
//    jsonFile = jsonFromUrl("https://www.tezov.com/tezov_bank.catalog.json")
    configureProjects()

//    with("projectPath.dependencies"){
//        with("core"){
//            checkDependenciesVersion()
//        }
//        with("compose"){
//            checkDependenciesVersion()
//        }
//        with("lib"){
//            checkDependenciesVersion()
//        }
//    }

}

//allprojects {
//    tasks.withType(JavaCompile) {
//        options.compilerArgs.add("-Xlint:unchecked")
//        options.compilerArgs.add("-Xlint:deprecation")
//    }
//}