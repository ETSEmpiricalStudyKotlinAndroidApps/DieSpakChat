plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("name.remal.check-dependency-updates") version "1.0.211"
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("android.extensions")
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
        multiDexEnabled = true
        setProperty("archivesBaseName", "v$versionName ($versionCode)")
    }

    buildFeatures {
        dataBinding = true
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

dependencies {
    fun def(vararg dependencies: String) {
        for (dependency in dependencies) implementation(dependency)
    }

    def(
        "com.sfyc.ctpv:library:1.1.3",

        Dependencies.Firebase.Firestore,
        Dependencies.Firebase.Auth,
        Dependencies.Firebase.Config,
        Dependencies.Firebase.Database,
        Dependencies.Firebase.Messaging,
        Dependencies.Firebase.Storage,
        Dependencies.Firebase.Analytics,

        Dependencies.Rx.Room,
        Dependencies.Rx.Paging,

        Dependencies.Essential.AppCompat,
        Dependencies.Essential.Anko,
        Dependencies.Essential.Kotlin,
        Dependencies.Essential.LifeCycleExtensions,
        Dependencies.Essential.LifeCycleViewModel,

        Dependencies.Ktx.Core,
        Dependencies.Ktx.Fragment,

        Dependencies.Di.Hilt,

        Dependencies.Ui.FishBun,
        Dependencies.Ui.SmoothBottomBar,
        Dependencies.Ui.YoYo,
        Dependencies.Ui.Lottie,
        Dependencies.Ui.Material,
        Dependencies.Ui.Glide,
        Dependencies.Ui.CardView,
        Dependencies.Ui.ConstraintLayout,

        Dependencies.Util.TedPermission,
        Dependencies.Util.YoyoHelper,
        Dependencies.Util.AndroidUtils,
        Dependencies.Util.CrashReporter
    )

    kapt(Dependencies.Util.GlideCompiler)
    kapt(Dependencies.Di.HiltCompiler)
    kapt(Dependencies.Jetpack.RoomCompiler)
}