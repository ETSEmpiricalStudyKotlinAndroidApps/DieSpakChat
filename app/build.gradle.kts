plugins {
    id("com.android.application")
    // id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("name.remal.check-dependency-updates") version "1.2.2"
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin") // fix code-line locate
}

android {
    compileSdkVersion(Application.compileSdk)

    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
        versionCode = Application.versionCode
        versionName = Application.versionName
        ndk.debugSymbolLevel = "FULL"
        multiDexEnabled = true
        setProperty("archivesBaseName", "$versionName")
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/library_release.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }

    configurations {
        all {
            exclude(module = "protobuf-lite")
        }
    }
}

// Ignore red-line; work as well
dependencies {
    "implementation"(platform(Dependencies.firebase[0]))

    Dependencies.essential.forEach(::implementation)
    Dependencies.ktx.forEach(::implementation)
    Dependencies.jetpack.forEach(::implementation)
    Dependencies.ui.forEach(::implementation)
    Dependencies.util.forEach(::implementation)

    Dependencies.compiler.forEach(::kapt)
}
