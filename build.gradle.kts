buildscript {
    dependencies {
        projectPath.classpath.forEach {
            classpath(it.value)
        }
    }
    plugins {
        id(projectPath.plugin.application) version projectVersion.plugin.android apply false
        id(projectPath.plugin.library) version projectVersion.plugin.android apply false
        id(projectPath.plugin.kotlin) version projectVersion.plugin.kotlin apply false
        id(projectPath.plugin.kapt) version projectVersion.plugin.kapt apply false
        id(projectPath.plugin.ksp) version projectVersion.plugin.ksp apply false
        id(projectPath.plugin.tezov_project_config) version projectVersion.plugin.tezov_project apply false
    }
}

plugins {
    id(projectPath.plugin.tezov_project_catalog) version projectVersion.plugin.tezov_project apply true
}

tezovCatalog {

//    verboseCatalogBuild = true
    verbosePluginApply = true
//    verboseReadValue = true

    path = "F:/android_project/tezov_banque/tezov_bank.catalog.json"
    configureProjects()
}


//allprojects {
//    tasks.withType(JavaCompile) {
//        options.compilerArgs.add("-Xlint:unchecked")
//        options.compilerArgs.add("-Xlint:deprecation")
//    }
//}