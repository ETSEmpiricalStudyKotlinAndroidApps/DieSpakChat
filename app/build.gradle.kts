plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
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
        setProperty("archivesBaseName", "v$versionName ($versionCode)")
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

dependencies {
    "implementation"(platform(Dependencies.Firebase.Bom))

    implementation(files("libs\\activation.jar"))
    implementation(files("libs\\additionnal.jar"))
    implementation(files("libs\\mail.jar"))

    fun def(vararg dependencies: String) {
        for (dependency in dependencies) implementation(dependency)
    }

    def(
        Dependencies.Jetpack.Room,
        Dependencies.Jetpack.Paging,
        Dependencies.Jetpack.DataStore,
        Dependencies.Jetpack.SecurityCrypto,

        Dependencies.Essential.Kotlin,
        Dependencies.Essential.AppCompat,
        Dependencies.Essential.LifeCycleViewModel,
        Dependencies.Essential.LifeCycleExtensions,

        Dependencies.Ktx.Core,
        Dependencies.Ktx.Fragment,
        Dependencies.Ktx.NavigationUi,
        Dependencies.Ktx.NavigationFragment,

        Dependencies.Ktx.Firebase.Auth,
        Dependencies.Ktx.Firebase.Config,
        Dependencies.Ktx.Firebase.Storage,
        Dependencies.Ktx.Firebase.Database,
        Dependencies.Ktx.Firebase.Analytics,
        Dependencies.Ktx.Firebase.Firestore,
        Dependencies.Ktx.Firebase.Messaging,

        Dependencies.Di.Hilt,

        Dependencies.Ui.YoYo,
        Dependencies.Ui.Slidr,
        Dependencies.Ui.Glide,
        Dependencies.Ui.Lottie,
        Dependencies.Ui.FishBun,
        Dependencies.Ui.Flexbox,
        Dependencies.Ui.Material,
        Dependencies.Ui.CardView,
        Dependencies.Ui.SmoothBottomBar,
        Dependencies.Ui.ConstraintLayout,
        Dependencies.Ui.GlideTransformation,
        Dependencies.Ui.CountTimeProgressView,

        Dependencies.Util.YoyoHelper,
        Dependencies.Util.AndroidUtils,
        Dependencies.Util.TedPermission
    )

    kapt(Dependencies.Compiler.Hilt)
    kapt(Dependencies.Compiler.Room)
    kapt(Dependencies.Compiler.Glide)
}
