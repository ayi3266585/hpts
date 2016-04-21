# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk/tools/proguard/proguard-android.txt
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
#
#--------------- BEGIN: okhttp ----------
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
#--------------- END: okhttp ----------
#--------------- BEGIN: okio ----------
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
#--------------- END: okio ----------
-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**

#2.0.9后的不需要加下面这个keep
#-keep class org.xbill.DNS.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils
-keep class com.easemob.chatuidemo.utils.SmileUtils {*;}
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写(实际要去掉#)
#-keep class com.example.utils.SmileUtils {*;}
#如果使用easeui库，需要这么写
-keep class com.easemob.easeui.utils.EaseSmileUtils {*;}

#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}

#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

#Butterknife 混淆
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#eventbus 混淆
-keepclassmembers class ** {
    public void onEvent*(**);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# universal-image-loader 混淆

-dontwarn com.nostra13.universalimageloader.**

-keep class com.nostra13.universalimageloader.** { *; }

#Bugtags 混淆
-keepattributes LineNumberTable,SourceFile

-keep class com.bugtags.library.** {*;}
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.bugtags.library.**

# slidingmenu 的混淆

-dontwarn com.jeremyfeinstein.slidingmenu.lib.**

-keep class com.jeremyfeinstein.slidingmenu.lib.** { *; }
# v4 包的混淆

#-libraryjars ./libs/android-support-v4.jar

#-dontwarn android.support.**

-dontwarn android.support.v4.**

-dontwarn **CompatHoneycomb

-dontwarn **CompatHoneycombMR2

-dontwarn **CompatCreatorHoneycombMR2

-keep interface android.support.v4.app.** { *; }

-keep class android.support.v4.** { *; }

-keep public class * extends android.support.v4.**

-keep public class * extends android.app.Fragment
# 保持自定义控件类不被混淆

-keep public class * extends com.zhibaicc.android.adapter.PackageAdapter

-keepclasseswithmembers class * {

    public <init>(android.content.Context, android.util.AttributeSet);

}

-keepclasseswithmembers class * {

    public <init>(android.content.Context, android.util.AttributeSet, int);

}

-keepclassmembers class * implements android.os.Parcelable {

    static android.os.Parcelable$Creator CREATOR;

}

-keep public class * implements java.io.Serializable {

        public *;

}

