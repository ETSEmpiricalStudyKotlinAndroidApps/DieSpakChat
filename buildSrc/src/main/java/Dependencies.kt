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
        const val Bom = "26.1.0"
    }

    object Essential {
        const val Google = "4.3.3"
        const val AppCompat = "1.2.0"
        const val Anko = "0.10.8"
        const val Kotlin = "1.4.20"
        const val Gradle = "4.1.1"
        const val LifeCycleViewModel = "2.2.0"
        const val LifeCycleExtensions = "2.2.0"
    }

    object Ktx {
        const val Core = "1.3.2"
        const val Fragment = "2.3.1"
    }

    object Di {
        const val Dagger = "2.28.3"
        const val Hilt = "2.28-alpha"
    }

    object Jetpack {
        const val DataStore = "1.0.0-alpha01"
        const val Navigation = "2.3.1"
        const val Room = "2.3.0-alpha02"
        const val Paging = "2.1.2"
    }

    object Ui {
        const val FlexBox = "2.0.1"
        const val Slidr = "2.1.0"
        const val CountTimeProgressView = "1.1.3"
        const val FishBun = "1.0.0-alpha03"
        const val SmoothBottomBar = "1.7.6"
        const val YoYo = "2.4@aar"
        const val Lottie = "3.5.0"
        const val Material = "1.2.1"
        const val Glide = "4.11.0"
        const val GlideTransformation = "4.3.0"
        const val CardView = "1.0.0"
        const val ConstraintLayout = "2.0.4"
    }

    object Util {
        const val TedPermission = "2.2.3"
        const val YoYoHelper = "2.4@aar"
        const val AndroidUtils = "4.1.2"
        const val CarshReporter = "1.1.0"
    }
}

object Dependencies {
    object Firebase {
        const val Bom = "com.google.firebase:firebase-bom:${Versions.Firebase.Bom}"
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
        const val Storage = "com.google.firebase:firebase-storage-ktx"
        const val Database = "com.google.firebase:firebase-database-ktx"
        const val Config = "com.google.firebase:firebase-config-ktx"
        const val Analytics = "com.google.firebase:firebase-analytics-ktx"
        const val Auth = "com.google.firebase:firebase-auth-ktx"
        const val Messaging = "com.google.firebase:firebase-messaging-ktx"
        const val Firestore = "com.google.firebase:firebase-firestore-ktx"
    }

    object Di {
        const val Dagger = "com.google.dagger:dagger:${Versions.Di.Dagger}"
        const val DaggerCompiler = "com.google.dagger:dagger-compiler:${Versions.Di.Dagger}"
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Di.Hilt}"
        const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Di.Hilt}"
    }

    object Jetpack {
        const val DataStore =
            "androidx.datastore:datastore-preferences:${Versions.Jetpack.DataStore}"
        const val Room = "androidx.room:room-runtime:${Versions.Jetpack.Room}"
        const val RoomCompiler = "androidx.room:room-compiler:${Versions.Jetpack.Room}"
        const val Paging = "androidx.paging:paging-runtime:${Versions.Jetpack.Paging}"
    }

    object Ui {
        const val FlexBox = "com.google.android:flexbox:${Versions.Ui.FlexBox}"
        const val Slidr = "com.r0adkll:slidableactivity:${Versions.Ui.Slidr}"
        const val CountTimeProgressView =
            "com.sfyc.ctpv:library:${Versions.Ui.CountTimeProgressView}"
        const val FishBun = "com.sangcomz:FishBun:${Versions.Ui.FishBun}"
        const val SmoothBottomBar =
            "com.github.ibrahimsn98:SmoothBottomBar:${Versions.Ui.SmoothBottomBar}"
        const val YoYo = "com.daimajia.androidanimations:library:${Versions.Ui.YoYo}"
        const val Lottie = "com.airbnb.android:lottie:${Versions.Ui.Lottie}"
        const val Material = "com.google.android.material:material:${Versions.Ui.Material}"
        const val Glide = "com.github.bumptech.glide:glide:${Versions.Ui.Glide}"
        const val GlideTransformation =
            "jp.wasabeef:glide-transformations:${Versions.Ui.GlideTransformation}"
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