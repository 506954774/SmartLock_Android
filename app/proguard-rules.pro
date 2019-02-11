# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\AA\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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
-keepattributes SourceFile,LineNumberTable #保存混淆map,文件路径:project>module>build>outputs>{flavor name}>{build type}>mapping.txt
-ignorewarnings						# 忽略警告，避免打包时某些警告出现
-optimizationpasses 5				# 指定代码的压缩级别
-dontusemixedcaseclassnames			# 是否使用大小写混合
-dontskipnonpubliclibraryclasses	# 是否混淆第三方jar
-dontpreverify                      # 混淆时是否做预校验
-verbose                            # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法
-keepattributes Signature          #keep泛型
-keepattributes Exceptions         #异常
-keepattributes *Annotation*       #注解

-dontwarn android.support.v4.**     #缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错。
-dontwarn android.os.**
-keep class android.support.v4.** { *; } 		# 保持哪些类不被混淆
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class android.os.**{*;}

-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.widget
-keep public class * extends com.sqlcrypt.database
-keep public class * extends com.sqlcrypt.database.sqlite
-keep public class * extends com.treecore.**
-keep public class * extends de.greenrobot.dao.**

-dontwarn Decoder.**
-keep class Decoder.**{*;}


-dontwarn java.io.**
-keep class java.io.**{*;}



-keep class  * implements java.io.Serializable{*;}
-keep class  * implements android.os.Parcelable {*;}
-keep class  java.lang.ref.SoftReference{*;}

-keepclasseswithmembernames class * {		# 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {			 # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {			 # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity { #保持类成员
   public void *(android.view.View);
}

-keepclassmembers enum * {					# 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {	# 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}


-keep public class * implements java.lang.annotation.Annotation   #注解



####################################### 库 ################################################################
####################################### 库 ################################################################
####################################### 库 ################################################################


#*************************************************************
#*********************高德地图********************************
#*************************************************************
#3D 地图
-keep   class com.amap.api.mapcore.**{*;}
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}

#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
-keep   class com.amap.api.services.**{*;}

#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}


#*************************************************************
#*********************butterknife********************************
#*************************************************************
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


#*************************************************************
#********************* 友盟 ********************************
#*************************************************************
-dontwarn com.umeng.**
-keep class com.umeng.** { *; }


#*************************************************************
#********************* MOB ********************************
#*************************************************************
-dontwarn com.mob.**
-keep class com.mob.** { *; }


#*************************************************************
#********************* 环信 ********************************
#*************************************************************
#-keep class com.hyphenate.** {*;}
#-dontwarn  com.hyphenate.**
#*************************************************************
#*********************bolts(环信用到的)********************************
#*************************************************************
#-dontwarn bolts.**
#-keep class bolts.** { *; }

#*************************************************************
#*********************internal.org.apache(环信用到的)********************************
#*************************************************************
-dontwarn internal.org.apache.**
-keep class internal.org.apache.** { *; }
#*************************************************************
#*********************com.android.internal.http.multipart(环信用到的)********************************
#*************************************************************
-dontwarn com.android.internal.http.multipart.**
-keep class com.android.internal.http.multipart.** { *; }
#*************************************************************
#*********************com.parse(环信用到的)********************************
#*************************************************************
-dontwarn com.parse.**
-keep class com.parse.** { *; }
#*************************************************************
#*********************org.apache.commons.codec(环信用到的)********************************
#*************************************************************
-dontwarn org.apache.commons.codec.**
-keep class org.apache.commons.codec.** { *; }
#*************************************************************
#*********************apache相关(环信用到的)********************************
#*************************************************************
-dontwarn android.net.**
-keep class android.net.** { *; }
-dontwarn com.android.internal.http.multipart.**
-keep class com.android.internal.http.multipart.** { *; }
-dontwarn org.apache.**
-keep class org.apache.** { *; }


#*************************************************************
#********************* baidu ********************************
#*************************************************************
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**



#*************************************************************
#*********************Glide,Glide自定义配置类********************************
#*************************************************************
-dontwarn com.bumptech.**
-keep class com.bumptech.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#*************************************************************
#********************* retrofit2********************************
#*************************************************************
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }


#*************************************************************
#*********************greenDAO********************************
#*************************************************************
-dontwarn com.ilinklink.songjiong.green_dao.**
-keep class com.ilinklink.songjiong.green_dao.** { *; }

-dontwarn com.ilinklink.greendao.**
-keep class com.ilinklink.greendao.** { *; }

#*************************************************************
#*********************databinding********************************
#*************************************************************
-dontwarn android.databinding.**
-dontwarn com.android.databinding.**
-keep class android.databinding.** { *; }
-keep class com.android.databinding.** { *; }

#*************************************************************
#*********************BGA红点提示********************************
#*************************************************************
-dontwarn cn.bingoogolapple.**
-keep class cn.bingoogolapple.** { *; }

#*************************************************************
#*********************de.greenrobot********************************
#*************************************************************
-dontwarn de.greenrobot.**
-keep class de.greenrobot.** { *; }

#*************************************************************
#*********************freemarker.********************************
#*************************************************************
-dontwarn freemarker.**
-keep class freemarker.** { *; }


#*************************************************************
#********************* google********************************
#*************************************************************
-dontwarn com.google.**
-keep class com.google.** { *; }

#*************************************************************
#********************* org.hamcrest********************************
#*************************************************************
-dontwarn org.hamcrest.**
-keep class org.hamcrest.** { *; }

#*************************************************************
#********************* org.mozilla********************************
#*************************************************************
-dontwarn org.mozilla.**
-keep class org.mozilla.** { *; }

#*************************************************************
#********************* com.nineoldandroids动画相关********************************
#*************************************************************
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

#*************************************************************
#********************* okhttp3********************************
#*************************************************************
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

#*************************************************************
#********************* okio********************************
#*************************************************************
-dontwarn okio.**
-keep class okio.** { *; }

#*************************************************************
#********************* rxJAVA********************************
#*************************************************************
-dontwarn rx.**
-keep class rx.** { *; }

#*************************************************************
#*********************com.github.********************************
#*************************************************************
-dontwarn com.github.**
-keep class com.github.** { *; }


#*************************************************************
#*********************JPush********************************
#*************************************************************
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}


#*************************************************************
#*********************Multidex********************************
#*************************************************************
-dontwarn android.support.multidex.**
-keep class android.support.multidex.** { *; }


#*************************************************************
#*********************腾讯qq********************************
#*************************************************************
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}



#*************************************************************
#*********************tencent********************************
#*************************************************************
-dontwarn com.tencent.**
-keep class com.tencent.** { *; }



#*************************************************************
#*********************tencent********************************
#*************************************************************
-keep class com.tencent.mm.sdk.** {
   *;
}


#*************************************************************
#*********************jp********************************
#*********************jp.wasabeef:blurry:3.0.0 高斯模糊********************************
#*********************jp.co.cyberagent.android:gpuimage:2.0.3 图片滤镜********************************
#*************************************************************
-dontwarn jp.**
-keep class jp.** {
   *;
}



#*************************************************************
#*********************ali********************************
#*************************************************************
-dontwarn com.alipay.**
-keep class com.alipay.** {
   *;
}
-dontwarn HttpUtils.**
-keep class HttpUtils.** {
   *;
}
-dontwarn HttpUtils.**
-keep class HttpUtils.** {
   *;
}
-dontwarn com.ut.device.**
-keep class com.ut.device.** {
   *;
}

-keep class com.stx.xhb.xbanner.**{*;}


#*************************************************************
#*********************七牛********************************
#*************************************************************
-keep class org.json.**{*;}
-keep class com.qiniu.**{*;}
-keep class com.qiniu.android.**{*;}
-keep class com.qiniu.**{public <init>();}
-keep class com.pili.pldroid.player.** { *; }
-keep class com.qiniu.qplayer.mediaEngine.MediaPlayer{*;}
-ignorewarnings

#*************************************************************
#********************沉浸式********************************
#*************************************************************
-keep class com.gyf.barlibrary.* {*;}

#*************************************************************
#********************融云********************************
#*************************************************************
-keep class io.rong.** {*;}
-keep class cn.rongcloud.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent {*;}
-dontwarn io.rong.push.**
-dontnote com.xiaomi.**
-dontnote com.google.android.gms.gcm.**
-dontnote io.rong.**


#*************************************************************
#********************BaseRecyclerViewAdapterHelper*********
#https://github.com/CymChad/BaseRecyclerViewAdapterHelper
#*************************************************************

-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

#*************************************************************
#********************EventBus*********
#http://greenrobot.org/eventbus/documentation/proguard
#*************************************************************

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}