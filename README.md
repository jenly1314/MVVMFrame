# MVVMFrame

![Image](app/src/main/ic_launcher-web.png)

[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/MVVMFrame/master/app/release/app-release.apk)
[![](https://jitpack.io/v/jenly1314/MVVMFrame.svg)](https://jitpack.io/#jenly1314/MVVMFrame)
[![CI](https://travis-ci.org/jenly1314/MVVMFrame.svg?branch=master)](https://travis-ci.org/jenly1314/MVVMFrame)
[![API](https://img.shields.io/badge/API-16%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)
[![Blog](https://img.shields.io/badge/blog-Jenly-9933CC.svg)](http://blog.csdn.net/jenly121)

MVVMFrame for Android 是一个基于Google官方推出的Architecture Components dependencies （Lifecycle，LiveData，ViewModel，Room）构建的快速开发框架。有了MVVMFrame的加持，从此构建一个MVVM模式的项目变得快捷简单。

## 架构
![Image](image/mvvm_architecture.jpg)

## 引入

### Maven：
```maven
<dependency>
  <groupId>com.king.frame</groupId>
  <artifactId>mvvmframe</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
### Gradle:
```gradle
implementation 'com.king.frame:mvvmframe:1.0.0'
```
### Lvy:
```lvy
<dependency org='com.king.frame' name='mvvmframe' rev='1.0.0'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
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

Step.1 自定义全局配置(继承MVVMFrame中的FrameConfigModule)
```Java
/**
 * 自定义全局配置
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class ConfigModule extends FrameConfigModule {
    @Override
    public void applyOptions(Context context, com.king.frame.mvvmframe.di.module.ConfigModule.Builder builder) {
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
Step.2 在你项目中的AndroidManifest.xml中通过配置meta-data来自定义全局配置
```Xml
<!-- MVVMFrame 全局配置 -->
<meta-data android:name="com.king.mvvmframe.app.config.ConfigModule"
           android:value="FrameConfigModule"/>
```
Step.3 用你项目的Application继承MVVMFrame中的BaseApplication
```Java
/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .appComponent(getAppComponent())
                .build();
        //注入
        appComponent.inject(this);

    }


}
```

更多使用详情，请查看[app](app)中的源码使用示例或查看[Wiki](https://github.com/jenly1314/MVVMFrame/wiki)

## 版本记录
#### v1.0.0 &ensp;&ensp; 2018-12-12
*  MVVMFrame初始版本

## 关于我
   Name: <a title="关于作者" href="https://about.me/jenly1314" target="_blank">Jenly</a>

   Email: <a title="欢迎邮件与我交流" href="mailto:jenly1314@gmail.com" target="_blank">jenly1314#gmail.com</a> / <a title="给我发邮件" href="mailto:jenly1314@vip.qq.com" target="_blank">jenly1314#vip.qq.com</a>

   CSDN: <a title="CSDN博客" href="http://blog.csdn.net/jenly121" target="_blank">jenly121</a>

   Github: <a title="Github开源项目" href="https://github.com/jenly1314" target="_blank">jenly1314</a>

   微信公众号:

   ![公众号](http://olambmg9j.bkt.clouddn.com/jenly666.jpg)

   加入QQ群: <a title="点击加入QQ群" href="http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad" target="_blank">20867961</a>


   