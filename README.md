# MVVMFrame

![Image](app/src/main/ic_launcher-web.png)

[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/MVVMFrame/master/app/release/app-release.apk)
[![JCenter](https://img.shields.io/badge/JCenter-1.1.2-46C018.svg)](https://bintray.com/beta/#/jenly/maven/mvvmframe)
[![JitPack](https://jitpack.io/v/jenly1314/MVVMFrame.svg)](https://jitpack.io/#jenly1314/MVVMFrame)
[![CI](https://travis-ci.org/jenly1314/MVVMFrame.svg?branch=master)](https://travis-ci.org/jenly1314/MVVMFrame)
[![CircleCI](https://circleci.com/gh/jenly1314/MVVMFrame.svg?style=svg)](https://circleci.com/gh/jenly1314/MVVMFrame)
[![API](https://img.shields.io/badge/API-16%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)
[![Blog](https://img.shields.io/badge/blog-Jenly-9933CC.svg)](https://jenly1314.github.io/)
[![QQGroup](https://img.shields.io/badge/QQGroup-20867961-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad)

MVVMFrame for Android 是一个基于Google官方推出的Architecture Components dependencies （Lifecycle，LiveData，ViewModel，Room）构建的快速开发框架。有了MVVMFrame的加持，从此构建一个MVVM模式的项目变得快捷简单。

## 架构
![Image](image/mvvm_architecture.jpg)

## [Android version](https://github.com/jenly1314/MVVMFrame/tree/android)

## 引入

### Maven：
```maven
<dependency>
  <groupId>com.king.frame</groupId>
  <artifactId>mvvmframe</artifactId>
  <version>1.1.2</version>
  <type>pom</type>
</dependency>
```
### Gradle:
```gradle
//AndroidX 版本
implementation 'com.king.frame:mvvmframe:1.1.2'

//Android 版本
implementation 'com.king.frame:mvvmframe:1.0.2'
```
### Lvy:
```lvy
<dependency org='com.king.frame' name='mvvmframe' rev='1.1.2'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```

### **Dagger**和 **Room** 的注解处理器

   您需要引入下面的列出的编译时的注解处理器，用于自动生成相关代码。其它对应版本具体详情可查看 [Versions](https://github.com/jenly1314/MVVMFrame/releases)
```gradle
    //AndroidX ------------------ MVVMFrame v1.1.2
    //dagger
    annotationProcessor 'com.google.dagger:dagger-android-processor:2.27'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.27'
    //room
    annotationProcessor 'androidx.room:room-compiler:2.2.5'

```
```gradle
    // Android ------------------ MVVMFrame v1.0.2
    //dagger
    annotationProcessor 'com.google.dagger:dagger-android-processor:2.19'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.19'
    //room
    annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'

```

### MVVMFrame引入的库（具体对应版本请查看 [Versions](versions.gradle)）
```gradle
    //appcompat
    compileOnly deps.appcompat

    //retrofit
    api deps.retrofit.retrofit
    api deps.retrofit.gson
    api deps.retrofit.converter_gson

    //lifecycle
    api deps.lifecycle.runtime
    api deps.lifecycle.extensions
    annotationProcessor deps.lifecycle.compiler

    //room
    api deps.room.runtime
    annotationProcessor deps.room.compiler

    //dagger
    api deps.dagger.dagger
    api deps.dagger.android
    api deps.dagger.android_support
    annotationProcessor deps.dagger.android_processor
    annotationProcessor deps.dagger.compiler

    //log
    api deps.timber

```

###### 如果Gradle出现compile失败的情况，可以在Project的build.gradle里面添加如下：（也可以使用上面的GitPack来complie）
```gradle
allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/jenly/maven' }
    }
}
```

## 示例

集成步骤代码示例 （示例出自于[app](app)中）

Step.1 启用DataBinding，在你项目中的build.gradle的android{}中添加配置：
```gradle
dataBinding {
    enabled true
}

```

Step.2 使用JDK8编译（v1.1.2新增），在你项目中的build.gradle的android{}中添加配置：
```gradle
compileOptions {
    targetCompatibility JavaVersion.VERSION_1_8
    sourceCompatibility JavaVersion.VERSION_1_8
}

```

Step.3 自定义全局配置(继承MVVMFrame中的FrameConfigModule)
```Java
/**
 * 自定义全局配置
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class AppConfigModule extends FrameConfigModule {
    @Override
    public void applyOptions(Context context, ConfigModule.Builder builder) {
        builder.baseUrl(Constants.BASE_URL)//TODO 配置Retrofit中的baseUrl
                .retrofitOptions(new RetrofitOptions() {
            @Override
            public void applyOptions(Retrofit.Builder builder) {
                //TODO 配置Retrofit
                //如想使用RxJava
                //builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            }
        })
        .okHttpClientOptions(new OkHttpClientOptions() {
            @Override
            public void applyOptions(OkHttpClient.Builder builder) {
                //TODO 配置OkHttpClient
            }
        })
        .gsonOptions(new GsonOptions() {
            @Override
            public void applyOptions(GsonBuilder builder) {
                //TODO 配置Gson
            }
        });
    }
}
```

Step.4 在你项目中的AndroidManifest.xml中通过配置meta-data来自定义全局配置
```Xml
<!-- MVVMFrame 全局配置 -->
<meta-data android:name="com.king.mvvmframe.app.config.AppConfigModule"
           android:value="FrameConfigModule"/>
```

Step.4 用你项目的Application继承MVVMFrame中的BaseApplication
```Java
/**
 *  MVVMFrame 框架基于Google官方的Architecture Components dependencies 构建，在使用MVVMFrame时，需遵循一些规范：
 *  1.你的项目中的Application中需初始化MVVMFrame框架相关信息，有两种方式处理：
 *      a.直接继承本类{@link BaseApplication}即可；
 *      b.如你的项目中的Application本身继承了其它第三方的Application，因为Java是单继承原因，导致没法继承本类，可参照{@link BaseApplication}类，
 *      将{@link BaseApplication}中相关代码复制到你项目的Application中，在相应的生命周期中调用即可。
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //开始构建项目时，DaggerApplicationComponent类可能不存在，您需要执行Make Project才能生成，Make Project快捷键 Ctrl + F9
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .appComponent(getAppComponent())
                .build();
        //注入
        appComponent.inject(this);

    }


}
```

[Kotlin Demo](https://github.com/jenly1314/KingWeather)

更多使用详情，请查看[app](app)中的源码使用示例或直接查看[API帮助文档](https://jenly1314.github.io/projects/MVVMFrame/doc/)

### 相关开源项目
##### [KingWeather](https://github.com/jenly1314/KingWeather)  一款天气预报APP
##### [EasyChat](https://github.com/yetel/EasyChatAndroidClient) 一款即时通讯APP
##### [AppTemplate](https://github.com/jenly1314/AppTemplate) 一款基于**MVVMFrame**构建的App模板


## 版本记录

#### v1.1.2：2020-4-5 
*  优化细节
*  更新Gradle至v5.6.4
*  更新Lifecycle至v2.2.0
*  更新Room至v2.2.5
*  更新Dagger至v2.27
*  更新Retrofit至v2.8.1

#### v1.1.1：2019-11-4
*  优化部分细节
*  更新编译SDK至29
*  更新Gradle至v5.4.1
*  更新Lifecycle至v2.2.0-rc01
*  更新Room至v2.2.1
*  更新Dagger至v2.25.2
*  更新Retrofit至v2.6.2
*  更新Gson至v2.8.6

#### v1.1.0：2019-7-22
*  更新Dagger至v2.23.2
*  更新Gradle至v5.1.1
*  完全迁移至AndroidX版本

#### v1.0.2：2019-7-22
*  更新Dagger至v2.19
*  为迁移至AndroidX做准备（下一版本将直接发布AndroidX版）

#### v1.0.1：2019-7-9
*  Retrofit更新至v2.6.0

#### v1.0.0：2018-12-12
*  MVVMFrame初始版本

## 赞赏
如果您喜欢MVVMFrame，或感觉MVVMFrame帮助到了您，可以点右上角“Star”支持一下，您的支持就是我的动力，谢谢 :smiley:<p>
您也可以扫描下面的二维码，请作者喝杯咖啡 :coffee:
    <div>
        <img src="https://jenly1314.github.io/image/pay/wxpay.png" width="280" heght="350">
        <img src="https://jenly1314.github.io/image/pay/alipay.png" width="280" heght="350">
        <img src="https://jenly1314.github.io/image/pay/qqpay.png" width="280" heght="350">
        <img src="https://jenly1314.github.io/image/alipay_red_envelopes.jpg" width="233" heght="350">
    </div>

## 关于我
   Name: <a title="关于作者" href="https://about.me/jenly1314" target="_blank">Jenly</a>

   Email: <a title="欢迎邮件与我交流" href="mailto:jenly1314@gmail.com" target="_blank">jenly1314#gmail.com</a> / <a title="给我发邮件" href="mailto:jenly1314@vip.qq.com" target="_blank">jenly1314#vip.qq.com</a>

   CSDN: <a title="CSDN博客" href="http://blog.csdn.net/jenly121" target="_blank">jenly121</a>

   CNBlogs: <a title="博客园" href="https://www.cnblogs.com/jenly" target="_blank">jenly</a>

   Github: <a title="Github开源项目" href="https://github.com/jenly1314" target="_blank">jenly1314</a>

   加入QQ群: <a title="点击加入QQ群" href="http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad" target="_blank">20867961</a>
   <div>
       <img src="https://jenly1314.github.io/image/jenly666.png">
       <img src="https://jenly1314.github.io/image/qqgourp.png">
   </div>


   
