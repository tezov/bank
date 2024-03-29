plugins {
    id("com.tezov.plugin_project.catalog")
}

tezovCatalog {
    catalogFile = catalogFromFile("${project.projectDir}/catalog.bank.yaml")
//    catalogFile = catalogFromUrl("https://raw.githubusercontent.com/tezov/bank/dev/split_modules/catalog.bank.yaml")
    configureProjects()

//    val ignore_alpha = true
//    val ignore_beta = false
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