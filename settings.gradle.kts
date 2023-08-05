rootProject.name = "bank"
include(
    ":app",
    ":lib_adr_sdk_core",
    ":lib_adr_core",
//    ":lib_test_common",
//    ":lib_test_common_unit",
//    ":lib_test_common_integration",
)

project(":lib_adr_sdk_core")
    .projectDir = file("../lib_adr_sdk_core/")
project(":lib_adr_core")
    .projectDir = file("../lib_adr_core/")


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
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath("com.tezov:plugin_project:1.0.2")
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



