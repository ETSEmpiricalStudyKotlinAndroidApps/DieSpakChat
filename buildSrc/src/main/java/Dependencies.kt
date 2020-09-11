import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val jvmTarget = "1.8"
    const val versionCode = 1
    const val versionName = "1.0.0"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    object Firebase {
        const val DataBase = "19.4.0"
        const val Storage = "19.2.0"
        const val Firestore = "21.6.0"
        const val Messaging = "20.2.4"
        const val Config = "19.2.0"
        const val Auth = "19.3.2"
        const val Analytics = "17.5.0"
    }

    object Essential {
        const val Google = "4.3.3"
        const val AppCompat = "1.2.0"
        const val Anko = "0.10.8"
        const val Kotlin = "1.4.10"
        const val Gradle = "4.0.1"
        const val LifeCycleViewModel = "2.2.0"
        const val LifeCycleExtensions = "2.2.0"
    }

    object Ktx {
        const val Core = "1.3.1"
        const val Fragment = "2.3.0"
    }

    object Di {
        const val Dagger = "2.29"
    }

    object Jetpack {
        const val Navigation = "2.3.0"
        const val Room = "2.3.0-alpha02"
        const val Paging = "2.1.2"
    }

    object Ui {
        const val FishBun = "1.0.0-alpha03"
        const val SmoothBottomBar = "1.7.6"
        const val YoYo = "2.4@aar"
        const val Lottie = "3.4.2"
        const val Material = "1.2.1"
        const val Glide = "4.11.0"
        const val CardView = "1.0.0"
        const val ConstraintLayout = "2.0.1"
    }

    object Util {
        const val TedPermission = "2.2.3"
        const val YoYoHelper = "2.4@aar"
        const val AndroidUtils = "4.0.1"
        const val CarshReporter = "1.1.0"
    }
}

object Dependencies {
    object Firebase {
        const val Firestore =
            "com.google.firebase:firebase-firestore:${Versions.Firebase.Firestore}"
        const val Auth = "com.google.firebase:firebase-auth:${Versions.Firebase.Auth}"
        const val Database = "com.google.firebase:firebase-database:${Versions.Firebase.DataBase}"
        const val Storage = "com.google.firebase:firebase-storage:${Versions.Firebase.Storage}"
        const val Messaging =
            "com.google.firebase:firebase-messaging:${Versions.Firebase.Messaging}"
        const val Config = "com.google.firebase:firebase-config:${Versions.Firebase.Config}"
        const val Analytics =
            "com.google.firebase:firebase-analytics:${Versions.Firebase.Analytics}"
    }

    object Rx {
        const val Paging = "androidx.paging:paging-rxjava2:${Versions.Jetpack.Paging}"
        const val Room = "androidx.room:room-rxjava2:${Versions.Jetpack.Room}"
    }

    object Essential {
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.Essential.AppCompat}"
        const val Anko = "org.jetbrains.anko:anko:${Versions.Essential.Anko}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}"
        const val LifeCycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.Essential.LifeCycleExtensions}"
        const val LifeCycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Essential.LifeCycleViewModel}"
    }

    object Ktx {
        const val NavigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Jetpack.Navigation}"
        const val NavigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.Jetpack.Navigation}"
        const val Core = "androidx.core:core-ktx:${Versions.Ktx.Core}"
        const val Fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.Ktx.Fragment}"
    }

    object Di {
        const val Dagger = "com.google.dagger:dagger:${Versions.Di.Dagger}"
        const val DaggerCompiler = "com.google.dagger:dagger-compiler:${Versions.Di.Dagger}"
    }

    object Jetpack {
        const val Room = "androidx.room:room-runtime:${Versions.Jetpack.Room}"
        const val RoomCompiler = "androidx.room:room-compiler:${Versions.Jetpack.Room}"
        const val Paging = "androidx.paging:paging-runtime:${Versions.Jetpack.Paging}"
    }

    object Ui {
        const val FishBun = "com.sangcomz:FishBun:${Versions.Ui.FishBun}"
        const val SmoothBottomBar =
            "com.github.ibrahimsn98:SmoothBottomBar:${Versions.Ui.SmoothBottomBar}"
        const val YoYo = "com.daimajia.androidanimations:library:${Versions.Ui.YoYo}"
        const val Lottie = "com.airbnb.android:lottie:${Versions.Ui.Lottie}"
        const val Material = "com.google.android.material:material:${Versions.Ui.Material}"
        const val Glide = "com.github.bumptech.glide:glide:${Versions.Ui.Glide}"
        const val CardView = "androidx.cardview:cardview:${Versions.Ui.CardView}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}"
    }

    object Util {
        const val TedPermission = "gun0912.ted:tedpermission:${Versions.Util.TedPermission}"
        const val GlideCompiler = "com.github.bumptech.glide:compiler:${Versions.Ui.Glide}"
        const val YoyoHelper = "com.daimajia.easing:library:${Versions.Util.YoYoHelper}"
        const val AndroidUtils = "com.github.sungbin5304:SBT:${Versions.Util.AndroidUtils}"
        const val CrashReporter =
            "com.balsikandar.android:crashreporter:${Versions.Util.CarshReporter}"
    }
}