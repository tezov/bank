rootProject.name = "bank"
include(
    ":app",
    ":lib_adr_app_core",
    ":lib_adr_ui_cpt",
    ":lib_adr_ui_core",
    ":lib_adr_core",
    ":lib_kmm_core",
//    ":lib_test_common",
//    ":lib_test_common_unit",
//    ":lib_test_common_integration",
)

val libAdrBasePath = "../libs/lib_adr/"
val libKmmBasePath = "../libs/lib_kmm/"
//val libTestBasePath = "../libs/lib_test/"

project(":lib_adr_app_core")
    .projectDir = file("${libAdrBasePath}lib_adr_app_core/")
project(":lib_adr_ui_cpt")
    .projectDir = file("${libAdrBasePath}lib_adr_ui_cpt/")
project(":lib_adr_ui_core")
    .projectDir = file("${libAdrBasePath}lib_adr_ui_core/")
project(":lib_adr_core")
    .projectDir = file("${libAdrBasePath}lib_adr_core/")
project(":lib_kmm_core")
    .projectDir = file("${libKmmBasePath}lib_kmm_core/")


//project(":lib_test_common")
//    .projectDir = file("../lib_test/test_common/")
//project(":lib_test_common_unit")
//    .projectDir = file("../lib_test/test_common_unit/")
//project(":lib_test_common_integration")
//    .projectDir = file("../lib_test/test_common_integration/")

pluginManagement {

    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

buildscript {

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath("com.tezov:plugin_project:1.0.5")
    }

    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}



