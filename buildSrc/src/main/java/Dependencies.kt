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
    object Firebase {
        const val Bom = "com.google.firebase:firebase-bom:${Versions.Firebase.Bom}"
    }

    object Essential {
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.Essential.AppCompat}"
        const val LifeCycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Essential.LifeCycleViewModel}"
        const val LifeCycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.Essential.LifeCycleExtensions}"
    }

    object Ktx {
        const val Room = "androidx.room:room-ktx:${Versions.Jetpack.Room}"
        const val Core = "androidx.core:core-ktx:${Versions.Ktx.Core}"
        const val Fragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Ktx.Fragment}"
        const val NavigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.Jetpack.Navigation}"
        const val NavigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Jetpack.Navigation}"

        object Firebase {
            const val Auth = "com.google.firebase:firebase-auth-ktx"
            const val Config = "com.google.firebase:firebase-config-ktx"
            const val Storage = "com.google.firebase:firebase-storage-ktx"
            const val Database = "com.google.firebase:firebase-database-ktx"
            const val Analytics = "com.google.firebase:firebase-analytics-ktx"
            const val Messaging = "com.google.firebase:firebase-messaging-ktx"
            const val Firestore = "com.google.firebase:firebase-firestore-ktx"
        }
    }

    object Di {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Di.Hilt}"
    }

    object Jetpack {
        const val Room = "androidx.room:room-runtime:${Versions.Jetpack.Room}"
        const val Paging = "androidx.paging:paging-runtime:${Versions.Jetpack.Paging}"
        const val DataStore =
            "androidx.datastore:datastore-preferences:${Versions.Jetpack.DataStore}"
        const val SecurityCrypto =
            "androidx.security:security-crypto:${Versions.Jetpack.SecurityCrypto}"
    }

    object Ui {
        const val YoYo = "com.daimajia.androidanimations:library:${Versions.Ui.YoYo}"
        const val Glide = "com.github.bumptech.glide:glide:${Versions.Ui.Glide}"
        const val Slidr = "com.r0adkll:slidableactivity:${Versions.Ui.Slidr}"
        const val Lottie = "com.airbnb.android:lottie:${Versions.Ui.Lottie}"
        const val Flexbox = "com.google.android:flexbox:${Versions.Ui.Flexbox}"
        const val FishBun = "com.sangcomz:FishBun:${Versions.Ui.FishBun}"
        const val Material = "com.google.android.material:material:${Versions.Ui.Material}"
        const val CardView = "androidx.cardview:cardview:${Versions.Ui.CardView}"
        const val SmoothBottomBar =
            "com.github.ibrahimsn98:SmoothBottomBar:${Versions.Ui.SmoothBottomBar}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}"
        const val GlideTransformation =
            "jp.wasabeef:glide-transformations:${Versions.Ui.GlideTransformation}"
        const val CountTimeProgressView =
            "com.sfyc.ctpv:library:${Versions.Ui.CountTimeProgressView}"
    }

    object Util {
        const val YoyoHelper = "com.daimajia.easing:library:${Versions.Util.YoYoHelper}"
        const val AndroidUtils =
            "me.sungbin:androidutils:${Versions.Util.AndroidUtils}"
        const val CrashReporter =
            "com.balsikandar.android:crashreporter:${Versions.Util.CarshReporter}"
        const val TedPermission = "gun0912.ted:tedpermission:${Versions.Util.TedPermission}"
    }

    object Compiler {
        const val Hilt = "com.google.dagger:hilt-android-compiler:${Versions.Di.Hilt}"
        const val Room = "androidx.room:room-compiler:${Versions.Jetpack.Room}"
        const val Glide = "com.github.bumptech.glide:compiler:${Versions.Ui.Glide}"
    }
}
