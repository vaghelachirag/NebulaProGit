# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Admin\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
 -keep class android.support.v4.** { *; }
    -dontwarn android.support.v4.**
    -dontwarn javax.activation.**
    -dontwarn javax.security.**
    -dontwarn java.awt.**
    -dontwarn com.github.mikephil.charting.data.realm.**
    -dontwarn io.realm.**
    -libraryjars <java.home>/lib/rt.jar
    -keep class javax.** {*;}
    -keep class com.sun.** {*;}
    -keep class myjava.** {*;}
    -keep class org.apache.harmony.** {*;}
    -keep public class Mail {*;}

    -dontwarn org.apache.commons.**
    -keep class org.apache.http.** { *; }
    -dontwarn org.apache.http.**


    -dontwarn org.apache.**

    -keep class com.fasterxml.jackson.databind.ObjectMapper {
        public <methods>;
        protected <methods>;
    }
    -keep class com.fasterxml.jackson.databind.ObjectWriter {
        public ** writeValueAsString(**);
    }
    -keepnames class com.fasterxml.jackson.** { *; }
    -dontwarn com.fasterxml.jackson.databind.**

    -dontwarn com.squareup.okhttp.**

    -dontwarn com.squareup.picasso.**

    -dontwarn okio.**

    -dontwarn org.joda.convert.*


    -dontwarn javax.annotation.**
    -dontwarn com.squareup.okhttp3.**
    -keep class com.squareup.okhttp3.** { *; }
    -keep interface com.squareup.okhttp3.* { *; }
    -dontwarn javax.annotation.Nullable
    -dontwarn javax.annotation.ParametersAreNonnullByDefault
    -dontwarn javax.annotation.GuardedBy
    -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }
    -dontwarn okio.**
    -dontwarn org.apache.lang.**
    -dontwarn org.joda.time.**
    -dontwarn org.w3c.dom.**
    -dontwarn com.viewpagerindicator.**
    -keep class android.support.v4.** { *; }
    -dontnote android.support.v4.**
    -dontwarn retrofit2.Platform$Java8
    -keep class android.support.v4.app.** { *; }
    -keep interface android.support.v4.app.** { *; }
    -keep class android.support.v7.app.** { *; }
    -keep interface android.support.v7.app.** { *; }
    -keep class android.support.v7.widget.SearchView { *; }
    -keep class org.ocpsoft.prettytime.**

    -keep class com.estimote.sdk.* { *; }
    -keep interface com.estimote.sdk.* { *; }
    -dontwarn com.estimote.sdk.**

    -dontwarn com.beloo.widget.chipslayoutmanager.Orientation
    -keep class com.beloo.widget.chipslayoutmanager.* { *; }
    -keep class com.beloo.widget.chipslayoutmanager.** { *; }
    -keep class com.beloo.widget.chipslayoutmanager.*$* { *; }
    -keep class RestrictTo.*
    -keep class RestrictTo.**
    -keep class RestrictTo.*$*

    -keep class org.ocpsoft.prettytime.i18n.**

    -keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
        boolean mShiftingMode;
    }

-dontwarn com.instagram.common.json.**
-dontwarn com.fasterxml.jackson.databind.ext.DOMSerializer
-dontwarn com.squareup.javawriter.JavaWriter
-dontwarn com.google.common.primitives.UnsignedBytes*

-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.CheckReturnValue
-dontwarn javax.annotation.CheckForNull
-dontwarn javax.annotation.concurrent.GuardedBy
-dontwarn javax.annotation.concurrent.Immutable
-dontwarn javax.annotation.concurrent.ThreadSafe
-dontwarn javax.annotation.concurrent.NotThreadSafe
    -keep class com.crashlytics.** { *; }
    -dontwarn com.crashlytics.**
    -keepattributes SourceFile,LineNumberTable
    -keep public class * extends java.lang.Exception
    -dontwarn com.google.android.gms.internal.zzhu
    -keep class com.google.android.gms.internal.** { *; }
    -dontwarn com.google.android.gms.ads.**



    #For JodaTime
    #https://stackoverflow.com/questions/14025487/proguard-didnt-compile-with-joda-time-used-in-windows
    -dontwarn org.joda.convert.FromString
    -dontwarn org.joda.convert.ToString

    # support-v4
    #https://stackoverflow.com/questions/18978706/obfuscate-android-support-v7-widget-gridlayout-issue
    -dontwarn android.support.v4.**
    -keep class android.support.v4.app.** { *; }
    -keep interface android.support.v4.app.** { *; }
    -keep class android.support.v4.** { *; }


    # support-v7
    -dontwarn android.support.v7.**
    -keep class android.support.v7.internal.** { *; }
    -keep interface android.support.v7.internal.** { *; }
    -keep class android.support.v7.** { *; }

    # support design
    #@link http://stackoverflow.com/a/31028536
    -dontwarn android.support.design.**
    -keep class android.support.design.** { *; }
    -keep interface android.support.design.** { *; }
    -keep public class android.support.design.R$* { *; }

    #error : Note: the configuration refers to the unknown class 'com.google.vending.licensing.ILicensingService'
    #solution : @link http://stackoverflow.com/a/14463528
    -dontnote com.google.vending.licensing.ILicensingService
    -dontnote **ILicensingService

    #social auth
    -keep class org.brickred.** { *;}
    -dontwarn org.brickred.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-dontwarn java.lang.ClassValue
-dontwarn com.google.common.base.**
-keep class com.google.common.base.** {*;}
-dontwarn com.google.errorprone.annotations.**
-keep class com.google.errorprone.annotations.** {*;}
-dontwarn com.google.j2objc.annotations.**
-keep class com.google.j2objc.annotations.** { *; }
-dontwarn java.lang.ClassValue
-keep class java.lang.ClassValue { *; }
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keep class org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement { *; }

-keepclassmembers class * {
  @com.google.api.client.util.Key <fields>;
}
-dontwarn java.lang.invoke.**

-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault,*Annotation*

-dontwarn sun.misc.Unsafe
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


-dontwarn org.apache.commons.**
-dontwarn com.google.**
-dontwarn com.j256.ormlite**
-dontwarn org.apache.http**

-keepattributes SourceFile,LineNumberTable
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }

-keepattributes Signature
# GSON Library
# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Google Map
-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }
-dontwarn android.support.**
-dontwarn io.card.**

-keepnames @com.google.android.gms.common.annotation.KeepName class
    com.google.android.gms.**,
    com.google.ads.**

-keepclassmembernames class
    com.google.android.gms.**,
    com.google.ads.** {
    @com.google.android.gms.common.annotation.KeepName *;
}

# Called by introspection
-keep class
    com.google.android.gms.**,
    com.google.ads.**
    extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
}


# This keeps the class name as well as the creator field, because the
# "safe parcelable" can require them during unmarshalling.
-keepnames class
    com.google.android.gms.**,
    com.google.ads.**
    implements android.os.Parcelable {
    public static final ** CREATOR;
}

# com.google.android.gms.auth.api.signin.SignInApiOptions$Builder
# references these classes but no implementation is provided.
-dontnote com.facebook.Session
-dontnote com.facebook.FacebookSdk
-keepnames class com.facebook.Session {}
-keepnames class com.facebook.FacebookSdk {}

# android.app.Notification.setLatestEventInfo() was removed in
# Marsmallow, but is still referenced (safely)
-dontwarn com.google.android.gms.common.GooglePlayServicesUtil

-keep public class your.domain.package.* {
  public *** get*();
  public void set*(***);
}


-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**


-dontwarn org.apache.http.**
-dontwarn android.net.**
-keep class org.apache.** {*;}
-keep class org.apache.http.** { *; }

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault


-dontwarn com.google.ads.**
-dontshrink

-dontoptimize

-dontobfuscate

-keep,allowshrinking,allowoptimization,allowobfuscation class org.mypackage.MyClass
    #similarly handle other libraries you added
    #-renamesourcefileattribute SourceFile
    #-keepattributes SourceFile,LineNumberTable

    #-printmapping mapping.txt

    #-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


    -assumenosideeffects class android.util.Log {
        public static boolean isLoggable(java.lang.String, int);
        public static int v(...);
        public static int i(...);
        public static int w(...);
        public static int d(...);
        public static int e(...);
    }