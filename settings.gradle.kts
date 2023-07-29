rootProject.name = "bank"
include(
    ":app",
    ":lib_core_kotlin",
    ":lib_core_android_kotlin",
    ":lib_test_common",
    ":lib_test_common_unit",
    ":lib_test_common_integration",
)

project(":lib_core_kotlin")
    .projectDir = file("../lib_core_kotlin/")
project(":lib_core_android_kotlin")
    .projectDir = file("../lib_core_android_kotlin/")

project(":lib_test_common")
    .projectDir = file("../lib_test/test_common/")
project(":lib_test_common_unit")
    .projectDir = file("../lib_test/test_common_unit/")
project(":lib_test_common_integration")
    .projectDir = file("../lib_test/test_common_integration/")

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
        classpath("com.tezov:plugin_project:1.0.0-8.0.2+-alpha.12")
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
    }

    repositories {
        mavenLocal()
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



