-keepattributes SourceFile,LineNumberTable,Signature
-renamesourcefileattribute SourceFile

-keepclassmembers class me.sungbin.spakchat.model.message.Message {
    *;
}
-keepclassmembers class me.sungbin.spakchat.model.user.User {
    *;
}

-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}