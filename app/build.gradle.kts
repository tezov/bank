import java.io.FileInputStream
import java.util.Properties

tezovConfig {

    configuration {
        domain = tezovCatalog.string("domain")
//        languages.apply{
//            add("fr")
//            add("en")
//        }
    }

    version {
        major = 0
        minor = 1
        patch = 0
    }

    debug {
        keepProguard = true
        keepSourceFile = true
        repackage = false
        obfuscate = false
        minify = false
        hasJUnitRunner = true
    }

    release {
        enableDebug = true
//        proguards.apply {
//            add(File("proguards-rules.pro"))
//        }
    }

    configureAndroidPlugin()
}

android {

    lint {
        baseline = file("lint-baseline.xml")
    }

    tezovCatalog {
        with("projectVersion") {
            compileSdk = int("defaultCompileSdk")
            compileOptions {
                sourceCompatibility = javaVersion("javasource")
                targetCompatibility = javaVersion("javaTarget")
            }
            kotlinOptions {
                jvmTarget = javaVersion("jvmTarget").toString()
            }
            defaultConfig {
                minSdk = int("defaultMinCompileSdk")
                targetSdk = int("defaultTargetCompileSdk")
            }
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = string("composeCompiler")
            }
        }
        packaging {
            resources {
                stringListOrNull("resourcesExcluded")?.let {
                    excludes.addAll(it)
                }
            }
        }
    }
    signingConfigs {
        create("release") {
            val keystoreReleaseProperties = Properties().apply {
                load(file("${project.projectDir}/keyStoreRelease.properties").inputStream())
            }
            storeFile = file("${project.projectDir}/${keystoreReleaseProperties.getProperty("storeFile")}")
            storePassword = keystoreReleaseProperties.getProperty("storePassword")
            keyAlias = keystoreReleaseProperties.getProperty("keyAlias")
            keyPassword = keystoreReleaseProperties.getProperty("keyPassword")
        }
        getByName("debug") {
            val keystoreDebugProperties = Properties().apply {
                load(File("${project.projectDir}/keyStoreDebug.properties").inputStream())
            }
            storeFile = file("${project.projectDir}/${keystoreDebugProperties.getProperty("storeFile")}")
            storePassword =keystoreDebugProperties.getProperty("storePassword")
            keyAlias = keystoreDebugProperties.getProperty("keyAlias")
            keyPassword = keystoreDebugProperties.getProperty("keyPassword")
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            //signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":lib_core_android_kotlin"))
    tezovCatalog {
        with("projectPath.dependencies.core") {
            implementation(string("dagger"))
            kapt(string("dagger_kapt"))
        }
        with("projectPath.dependencies.lib") {
            implementation(string("threetenabp"))
            implementation(string("webkit"))
        }
    }

    // test
    testImplementation(project(":lib_test_common"))
    androidTestImplementation(project(":lib_test_common"))
    tezovCatalog {
        with("projectPath.dependencies_test.core_integration") {
            debugImplementation(string("compose_ui_manifest"))
        }
    }
}