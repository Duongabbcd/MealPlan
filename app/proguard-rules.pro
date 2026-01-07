# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.bytedance.sdk.** { *; }
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

#unity
-keep class com.ironsource.unity.androidbridge.** { *;}
-keep class com.google.android.gms.ads.** {public *;}
-keep class com.google.android.gms.appset.** { *; }
-keep class com.google.android.gms.tasks.** { *; }
#adapters
-keep class com.ironsource.adapters.** { *; }
#sdk
-dontwarn com.ironsource.**
-dontwarn com.ironsource.adapters.**
-keepclassmembers class com.ironsource.** { public *; }
-keep public class com.ironsource.**
-keep class com.ironsource.adapters.** { *;
}
#omid
-dontwarn com.iab.omid.**
-keep class com.iab.omid.** {*;}

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep public class * implements java.lang.reflect.Type
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class com.google.gson.examples.android.model.** { <fields>; }
-dontwarn sun.misc.**
-keepattributes *Annotation*
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep class com.adjust.sdk.** { *; }
-keep class com.adjust.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
   int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
   com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
   java.lang.String getId();
   boolean isLimitAdTrackingEnabled();
}
-keep class com.google.android.gms.ads.** {public *;}
-keep public class com.android.installreferrer.** { *; }
-keep public class com.adjust.sdk.** { *; }

-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-dontwarn net.premiumads.**
-dontwarn net.premiumads.sdk.**
-keepclassmembers class net.premiumads.** { public *; }
-keep public class net.premiumads.**
-keep class net.premiumads.sdk.** { *;}
-keep class net.premiumads.sdk.admob.** { *;}


-keepclassmembers class com.ezt.meal.ai.scan.model.*{
<fields>;
<init>();
<methods>;
}

-keep class com.ezt.meal.ai.scan.model.ApiResponse { *; }
-keep class com.ezt.meal.ai.scan.model.DataWrapper { *; }
-keep class com.ezt.meal.ai.scan.model.QueueInput { *; }
-keep class com.ezt.meal.ai.scan.model.QueueResponse { *; }
-keep class com.ezt.meal.ai.scan.model.TimeInfo { *; }
-keep class com.ezt.meal.ai.scan.model.NutritionResponse { *; }
-keep class com.ezt.meal.ai.scan.model.TotalNutrition { *; }
-keep class com.ezt.meal.ai.scan.model.Ingredient { *; }
-keep class com.ezt.meal.ai.scan.model.MeasurementUnits { *; }
-keep class com.ezt.meal.ai.scan.database.Meal { *; }


# Keep generic type info for Retrofit & Gson (especially for suspend functions)
-keepattributes Signature, Exceptions, *Annotation*

# Keep Retrofit annotations and method signatures
-keepclassmembers,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Keep Retrofit Response type
-keep class retrofit2.Response
-keep interface retrofit2.Call

# Prevent R8 from stripping suspend function metadata
-keep class kotlin.coroutines.Continuation

# Keep Gson TypeAdapters, Deserializers, and Serializers
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer