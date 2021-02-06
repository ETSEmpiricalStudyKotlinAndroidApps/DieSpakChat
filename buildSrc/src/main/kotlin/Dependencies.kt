import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val jvmTarget = "1.8"
    const val versionCode = 1
    const val versionName = "PLEASE_DIE_FUCKING_CORONA"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    object Firebase {
        const val Bom = "26.4.0"
    }

    object Essential {
        const val Google = "4.3.3"
        const val Kotlin = "1.4.30"
        const val Gradle = "4.1.2"
        const val AppCompat = "1.2.0"
        const val LifeCycleViewModel = "2.2.0"
        const val LifeCycleExtensions = "2.2.0"
    }

    object Ktx {
        const val Core = "1.3.2"
        const val Fragment = "2.3.2"
    }

    object Di {
        const val Hilt = "2.28-alpha"
    }

    object Jetpack {
        const val Room = "2.3.0-alpha04"
        const val Paging = "2.1.2"
        const val DataStore = "1.0.0-alpha01"
        const val Navigation = "2.3.3"
        const val SecurityCrypto = "1.1.0-alpha03"
    }

    object Ui {
        const val YoYo = "2.4@aar"
        const val Slidr = "2.1.0"
        const val Glide = "4.12.0"
        const val Lottie = "3.6.0"
        const val Flexbox = "2.0.1"
        const val FishBun = "1.0.0-alpha05"
        const val Material = "1.2.1"
        const val CardView = "1.0.0"
        const val SmoothBottomBar = "1.7.6"
        const val ConstraintLayout = "2.0.4"
        const val GlideTransformation = "4.3.0"
        const val CountTimeProgressView = "1.1.3"
    }

    object Util {
        const val YoYoHelper = "2.4@aar"
        const val AndroidUtils = "1.1.0"
        const val CarshReporter = "1.1.0"
        const val TedPermission = "2.2.3"
    }
}

object Dependencies {
    val firebase = listOf(
        "com.google.firebase:firebase-bom:${Versions.Firebase.Bom}"
    )

    val essential = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}",
        "androidx.appcompat:appcompat:${Versions.Essential.AppCompat}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Essential.LifeCycleViewModel}",
        "androidx.lifecycle:lifecycle-extensions:${Versions.Essential.LifeCycleExtensions}"
    )

    val ktx = listOf(
        "androidx.room:room-ktx:${Versions.Jetpack.Room}",
        "androidx.core:core-ktx:${Versions.Ktx.Core}",
        "androidx.navigation:navigation-fragment-ktx:${Versions.Ktx.Fragment}",
        "androidx.navigation:navigation-ui-ktx:${Versions.Jetpack.Navigation}",
        "androidx.navigation:navigation-fragment-ktx:${Versions.Jetpack.Navigation}",

        "com.google.firebase:firebase-auth-ktx",
        "com.google.firebase:firebase-config-ktx",
        "com.google.firebase:firebase-storage-ktx",
        "com.google.firebase:firebase-database-ktx",
        "com.google.firebase:firebase-analytics-ktx",
        "com.google.firebase:firebase-messaging-ktx",
        "com.google.firebase:firebase-firestore-ktx"
    )

    val di = listOf(
        "com.google.dagger:hilt-android:${Versions.Di.Hilt}"
    )

    val jetpack = listOf(
        "androidx.room:room-runtime:${Versions.Jetpack.Room}",
        "androidx.paging:paging-runtime:${Versions.Jetpack.Paging}",
        "androidx.datastore:datastore-preferences:${Versions.Jetpack.DataStore}",
        "androidx.security:security-crypto:${Versions.Jetpack.SecurityCrypto}"
    )

    val ui = listOf(
        "com.daimajia.androidanimations:library:${Versions.Ui.YoYo}",
        "com.github.bumptech.glide:glide:${Versions.Ui.Glide}",
        "com.r0adkll:slidableactivity:${Versions.Ui.Slidr}",
        "com.airbnb.android:lottie:${Versions.Ui.Lottie}",
        "com.google.android:flexbox:${Versions.Ui.Flexbox}",
        "com.sangcomz:FishBun:${Versions.Ui.FishBun}",
        "com.google.android.material:material:${Versions.Ui.Material}",
        "androidx.cardview:cardview:${Versions.Ui.CardView}",
        "com.github.ibrahimsn98:SmoothBottomBar:${Versions.Ui.SmoothBottomBar}",
        "androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}",
        "jp.wasabeef:glide-transformations:${Versions.Ui.GlideTransformation}",
        "com.sfyc.ctpv:library:${Versions.Ui.CountTimeProgressView}"
    )

    val util = listOf(
        "com.daimajia.easing:library:${Versions.Util.YoYoHelper}",
        "me.sungbin:androidutils:${Versions.Util.AndroidUtils}",
        "com.balsikandar.android:crashreporter:${Versions.Util.CarshReporter}",
        "gun0912.ted:tedpermission:${Versions.Util.TedPermission}"
    )

    val compiler = listOf(
        // "com.google.dagger:hilt-android-compiler:${Versions.Di.Hilt}",
        "androidx.room:room-compiler:${Versions.Jetpack.Room}",
        "com.github.bumptech.glide:compiler:${Versions.Ui.Glide}"
    )
}
