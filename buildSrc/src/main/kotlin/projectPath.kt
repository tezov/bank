

object projectPath {

    val classpath = mapOf(
        plugin.android to "com.android.tools.build:gradle:${projectVersion.plugin.android}",
        plugin.kotlin to "org.jetbrains.kotlin:kotlin-gradle-plugin:${projectVersion.plugin.kotlin}",
        plugin.tezov_project to "com.tezov:plugin_project:${projectVersion.plugin.tezov_project}",
    )

    object plugin {
        val android = "com.android"
        val application = "$android.application"
        val library = "$android.library"
        val kotlin = "org.jetbrains.kotlin.android"
        val kapt = "org.jetbrains.kotlin.kapt"
        val ksp = "com.google.devtools.ksp"
        val tezov_project = "com.tezov.plugin_project"
        val tezov_project_config = "$tezov_project.config"
        val tezov_project_catalog = "$tezov_project.catalog"
    }

}

