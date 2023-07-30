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
//    signingConfigs {
//
//        releaseConfig {
//            def keystoreReleaseProperties = new Properties()
//            keystoreReleaseProperties.load(new FileInputStream(file("keyStoreRelease.properties")))
//            storeFile file(keystoreReleaseProperties.storeFile)
//            storePassword keystoreReleaseProperties.storePassword
//            keyAlias keystoreReleaseProperties.keyAlias
//            keyPassword keystoreReleaseProperties.keyPassword
//        }
//
//        debugConfig {
//            def keystoreDebugProperties = new Properties()
//            keystoreDebugProperties.load(new FileInputStream(file("keyStoreDebug.properties")))
//            storeFile file(keystoreDebugProperties.storeFile)
//            storePassword keystoreDebugProperties.storePassword
//            keyAlias keystoreDebugProperties.keyAlias
//            keyPassword keystoreDebugProperties.keyPassword
//        }
//    }
    buildTypes {
        getByName("release") {
//            signingConfig signingConfigs.releaseConfig
        }
        getByName("debug") {
//            signingConfig signingConfigs.debugConfig
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