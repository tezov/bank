dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

pluginManagement {
    fun FlatDirectoryArtifactRepository.directories(fom: String) {
        val directories = mutableListOf<String>()
        File(fom).walkTopDown()
            .filter { it.isDirectory }
            .forEach { directories.add(it.absolutePath) }
        directories.forEach {
            dir(it)
        }
    }

    repositories {
        mavenLocal()
//        flatDir {
//            name = "repository"
//            directories("F:/android_project/repository/com/tezov/plugin_project")
//        }
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

rootProject.name = "bank"
include(
    ":app",
    ":lib_core_kotlin",
    ":lib_core_android_kotlin",
    ":test_common",
    ":test_common_unit",
    ":test_common_integration",
)

project(":lib_core_kotlin")
    .projectDir = file("../lib_core_kotlin/")
project(":lib_core_android_kotlin")
    .projectDir = file("../lib_core_android_kotlin/")

project(":test_common")
    .projectDir = file("../lib_test/test_common/")
project(":test_common_unit")
    .projectDir = file("../lib_test/test_common_unit/")
project(":test_common_integration")
    .projectDir = file("../lib_test/test_common_integration/")

