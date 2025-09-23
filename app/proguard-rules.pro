# Keep AndroidAnnotations generated classes (classes with underscore)
-keep class **_ { *; }

# Keep annotations themselves (required for reflection)
-keep @org.androidannotations.annotations.* class * { *; }

# Keep generated view binder classes
-keep class **$$ViewBinder { *; }

# Keep generated intent builder classes (if using @EActivity, @EFragment etc.)
-keep class **_.IntentBuilder { *; }

# Keep inner classes of generated fragments/activities
-keep class **_** { *; }

# Keep enums (optional, if used in models with annotations)
-keepclassmembers enum * { *; }

# Optional: Keep all classes in your package (if generated classes still cause issues)
-keep class com.infotech.fplcolosseum.** { *; }

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE