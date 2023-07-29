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

//    val ignore_alpha = true
//    val ignore_beta = true
//    val ignore_rc = false
//    with("projectPath.dependencies"){
//        with("core"){
//            checkDependenciesVersion(ignore_alpha, ignore_beta, ignore_rc)
//        }
//        with("compose"){
//            checkDependenciesVersion(ignore_alpha, ignore_beta, ignore_rc)
//        }
//        with("lib"){
//            checkDependenciesVersion(ignore_alpha, ignore_beta, ignore_rc)
//        }
//    }
//    with("projectPath.dependencies_test"){
//        with("core_integration"){
//            checkDependenciesVersion(ignore_alpha, ignore_beta, ignore_rc)
//        }
//        with("core_unit"){
//            checkDependenciesVersion(ignore_alpha, ignore_beta, ignore_rc)
//        }
//    }

}

//allprojects {
//    tasks.withType(JavaCompile) {
//        options.compilerArgs.add("-Xlint:unchecked")
//        options.compilerArgs.add("-Xlint:deprecation")
//    }
//}