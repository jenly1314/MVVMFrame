# MVVMFrame

![Image](app/src/main/ic_launcher-web.png)

[![MavenCentral](https://img.shields.io/maven-central/v/com.github.jenly1314/mvvmframe?logo=sonatype)](https://repo1.maven.org/maven2/com/github/jenly1314/MVVMFrame)
[![JitPack](https://img.shields.io/jitpack/v/github/jenly1314/MVVMFrame?logo=jitpack)](https://jitpack.io/#jenly1314/MVVMFrame)
[![CI](https://img.shields.io/github/actions/workflow/status/jenly1314/MVVMFrame/build.yml?logo=github)](https://github.com/jenly1314/MVVMFrame/actions/workflows/build.yml)
[![Download](https://img.shields.io/badge/download-APK-brightgreen?logo=github)](https://raw.githubusercontent.com/jenly1314/MVVMFrame/master/app/release/app-release.apk)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen?logo=android)](https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels)
[![License](https://img.shields.io/github/license/jenly1314/MVVMFrame?logo=open-source-initiative)](https://opensource.org/licenses/mit)

MVVMFrame for Android 是一个基于Google官方推出的Architecture Components dependencies（现在叫JetPack）构建的快速开发框架。有了 **MVVMFrame** 的加持，从此构建一个 **MVVM** 模式的项目变得快捷简单。

## 架构
![Image](art/mvvm_architecture.jpg)

## 引入

### Gradle：

1. 在Project的 **build.gradle** 或 **setting.gradle** 中添加远程仓库

    ```gradle
    repositories {
        //...
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    ```

2. 在Module的 **build.gradle** 中添加依赖项

    ```gradle
    implementation 'com.github.jenly1314:mvvmframe:3.1.0'
    ```

### Gradle Plugin（v3.0.0新增）

1. 首先，将 **MVVMFrame** 插件添加到项目根级 **build.gradle** 文件中：

    ```gradle
    plugins {
        //...
        id 'com.github.jenly1314.mvvmframe' version '3.1.0' apply false
    }
    ```

2. 接下来，在 **app/build.gradle** 文件中，添加 **MVVMFrame** 插件：

    ```gradle
    plugins {
        //...
        id 'com.github.jenly1314.mvvmframe'
    }
    ```

## 使用

### 版本特别说明

* 3.x版本统一改为使用`kotlin`并进行了重构；之前2.x版本内部使用的`LiveData`相关代码已全部移除，3.x版本已全部改用kotlin独有的`Flow`进行实现。

* 3.x相比较于2.x版本更为精简，可定制性更高。（如果需要在`Compose`中进行使用，只需加上`Compose`的UI相关依赖，稍微封装下即可）

> 建议在新项目中使用；如果你之前使用的是2.x旧版本，请谨慎升级。

> 如果你使用 **v2.x** 版本的话，请直接 [查看2.x分支版本](https://github.com/jenly1314/MVVMFrame/tree/2.x)

---

> 从分割线此处开始，以下全部为3.x版本相关说明

### 集成步骤说明（完整示例可直接查看[app](app)）

**Step.1** 启用 **ViewDataBinding** ，在你项目中的 **build.gradle** 的 **android{}** 中添加配置：

```gradle
buildFeatures{
    dataBinding = true
}
```

**Step.2** 使用JDK17编译，在你项目中的 **build.gradle** 的 **android{}** 中添加配置：

```gradle
compileOptions {
    targetCompatibility JavaVersion.VERSION_17
    sourceCompatibility JavaVersion.VERSION_17
}

```

**Step.3** 自定义全局配置(继承MVVMFrame中的`FrameConfigModule` )（提示：如果你没有自定义配置的需求，可以直接忽略此步骤）

```kotlin
/**
 * 全局配置
 */
class AppConfigModule : FrameConfigModule() {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {
        // 通过第一种方式初始化BaseUrl
        builder.baseUrl(Constants.BASE_URL) // TODO 配置Retrofit中的baseUrl

        builder.retrofitOptions(object : RetrofitOptions {
            override fun applyOptions(builder: Retrofit.Builder) {
                // TODO 配置Retrofit
            }
        })
            .okHttpClientOptions(object : OkHttpClientOptions {
                override fun applyOptions(builder: OkHttpClient.Builder) {
                    // TODO 配置OkHttpClient
                }
            })
            .gsonOptions(object : GsonOptions {
                override fun applyOptions(builder: GsonBuilder) {
                    // TODO 配置Gson
                }
            })
            .roomDatabaseOptions(object : RoomDatabaseOptions {
                override fun applyOptions(builder: RoomDatabase.Builder<out RoomDatabase>) {
                    // TODO 配置RoomDatabase
                }
            })
            .configOptions(object : AppliesOptions.ConfigOptions {
                override fun applyOptions(builder: Config.Builder) {
                    // TODO 配置Config
                    builder.httpLoggingLevel(
                        if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor.Level.BODY
                        } else {
                            HttpLoggingInterceptor.Level.NONE
                        }
                    )
                }
            })
    }
}
```

然后在你项目中的 **AndroidManifest.xml** 中通过配置`meta-data`来自定义全局配置（提示：如果你没有自定义配置的需求，可以直接忽略此步骤）
```xml
<!-- MVVMFrame 全局配置 -->
<meta-data android:name="com.king.mvvmframe.config.AppConfigModule"
           android:value="FrameConfigModule"/>
```
> 此处的`com.king.mvvmframe.config.AppConfigModule` 替换为你自定义的全局配置类

**Step.4** 配置`Application`

```kotlin
 @HiltAndroidApp
 class YourApplication : BaseApplication() {
    //...

    override fun onCreate() {
        super.onCreate()
        // 如果你没有使用FrameConfigModule中的第一中方式初始化BaseUrl，也可以通过此处的第二种方式来设置BaseUrl（二选其一即可）
//        RetrofitHelper.getInstance().setBaseUrl(baseUrl)
    }
 }
```
> 如果由于某种原因，导致你不能继承`BaseApplication`；你也可以在你自定义的`Application`的`onCreate`函
 数中通过调用`BaseApplication.initAppConfig`来进行初始化。

### 其他

### 关于使用 **Hilt**

**Hilt** 是JetPack中新增的一个依赖注入库，其基于 **Dagger2** 研发（后面统称为Dagger），但它不同于Dagger。对于Android开发者来说，Hilt可以说专门为Android 打造。

之前使用的 **Dagger for Android** 虽然也是针对于Android打造，也能通过 **@ContributesAndroidInjector** 来通过生成简化一部分样板代码，但是感觉还不够彻底。因为 **Component** 层相关的桥接还是要自己写。**Hilt** 的诞生改善了这些问题。

使用 **Hilt** 之后，依赖注入变得更简单。

#### **Hilt** 目前支持以下 **Android** 类：

* Application（通过使用 @HiltAndroidApp）
* ViewModel（通过使用 @HiltViewModel）
* Activity
* Fragment
* View
* Service
* BroadcastReceiver

**Application** 示例 (这里我们使用BaseApplication)
```kotlin
@HiltAndroidApp
class YourApplication : BaseApplication() {
    //...
}
```

**ViewModel** 示例 (这里我们使用BaseViewModel)
```kotlin
@HiltViewModel
class YourViewModel : BaseViewModel() {
    //...
}
```

其他的入口点，都是用 **@AndroidEntryPoint** 注解来声明，示例如下

**Activity** 示例 (这里我们使用BaseActivity)
```kotlin
@AndroidEntryPoint
class YourActivity: BaseActivity() {
    //...
}
```

**Fragment** 示例 (这里我们使用BaseFragment)
```kotlin
@AndroidEntryPoint
class YourFragment: BaseFragment() {
    //...
}
```
> 其他入口点都基本类似，不再一一列举了。更多有关 **Hilt** 的使用说明，可以查看官方的[Hilt使用指南](https://developer.android.google.cn/training/dependency-injection/hilt-android)。

### 关于设置 **BaseUrl**

> 目前通过设置 BaseUrl 的入口主要有两种：
>> 1.一种是通过在 Manifest 中配置 meta-data 的来自定义 FrameConfigModule,在里面 通过 {@link ConfigModule.Builder#baseUrl(String)}来配置 BaseUrl。（一次设置，全局配置）
>
>> 2.一种就是通过RetrofitHelper {@link RetrofitHelper#setBaseUrl(String)} 或 {@link RetrofitHelper#setBaseUrl(HttpUrl)} 来配置 BaseUrl。（可多次设置，动态全局配置，有前提条件）
>
> 以上两种配置 BaseUrl 的方式都可以达到目的。但是你可以根据不同的场景选择不同的配置方式。
>
> 主要场景与选择如下：
>
>> 一般场景：对于只使用单个不变的 BaseUrl的
>>>     场景1:如果本库的默认已满足你的需求，无需额外自定义配置的。
>          选择：建议你直接使用 {@link RetrofitHelper#setBaseUrl(String)} 或 {@link RetrofitHelper#setBaseUrl(HttpUrl)} 来初始化 BaseUrl，切记在框架配置初始化 BaseUrl之前，建议在你自定义的 {@link Application#onCreate()}中初始化。
>
>>>     场景2:如果本库的默认配置不满足你的需求，你需要自定义一些配置的。（比如需要使用 RxJava相关）
>          选择：建议你在自定义配置中通过 {@link ConfigModule.Builder#baseUrl(String)} 来初始化 BaseUrl。
>
>> 二般场景：对于只使用单个 BaseUrl 但是，BaseUrl中途会变动的。
>>>     场景3：和一般场景一样，也能分两种，所以选择也和一般场景也可以是一样的。
>          选择：两种选择都行，但当 BaseUrl需要中途变动时，还需将 {@link RetrofitHelper#setDynamicDomain(boolean)} 设置为 {@code true} 才能支持动态改变 BaseUrl。
>
>> 特殊场景：对于支持多个 BaseUrl 且支持动态可变的。
>>>        选择：这个场景的选择，主要涉及到另外的方法，请查看 {@link RetrofitHelper#putDomain(String, String)} 和 {@link RetrofitHelper#putDomain(String, HttpUrl)}相关详情


更多使用详情，请查看[app](app)中的源码使用示例或直接查看 [API帮助文档](https://jenly1314.github.io/MVVMFrame/api/)

## 混淆

 目前 **MVVFrame** 所有依赖混淆规则可参见：[ProGuard rules](mvvmframe/proguard-rules.pro)

## 相关推荐

- [AppTemplate](https://github.com/jenly1314/AppTemplate) 一款基于 **MVVMFrame** 构建的App模板
- [MVVMFrameComponent](https://github.com/jenly1314/MVVMFrameComponent) 一款基于 **MVVMFrame** 构建的组件化方案
- [EasyChat](https://github.com/yetel/EasyChatAndroidClient) 一款即时通讯APP
- [KingWeather](https://github.com/jenly1314/KingWeather)  一款天气预报APP
- [EasyNote](https://github.com/jenly1314/EasyNote) 一款遵循 **Clean Architecture** 架构分层， 使用 **Jetpack Compose** 实现的笔记App
- [RetrofitHelper](http://github.com/jenly1314/RetrofitHelper) 一个支持动态改变BaseUrl，动态配置超时时长的Retrofit帮助类。
- [AppUpdater](http://github.com/jenly1314/AppUpdater) 一个专注于App更新，一键傻瓜式集成App版本升级的轻量开源库。
- [LogX](http://github.com/jenly1314/LogX) 一个轻量而强大的日志框架；好用不解释。
- [KVCache](http://github.com/jenly1314/KVCache) 一个便于统一管理的键值缓存库；支持无缝切换缓存实现。
- [AndroidKTX](http://github.com/AndroidKTX/AndroidKTX) 一个简化 Android 开发的 Kotlin 工具类集合。
- [AndroidUtil](http://github.com/AndroidUtil/AndroidUtil) 一个整理了Android常用工具类集合，平时在开发的过程中可能会经常用到。

<!-- end -->

## 版本日志

#### v3.1.0：2025-3-23
* 优化插件配置
* 优化一些细节
* 更新gradle至v8.9
* 更新kotlin至v1.9.24
* 更新appcompat至v1.7.0
* 更新core-ktx至v1.13.1
* 更新fragment-ktx至v1.8.6
* 更新lifecycle-ktx至v2.8.7
* 更新hilt至v2.52
* 更新retrofit至v2.11.0
* 更新gson至v2.12.1

#### [查看更多版本记录](CHANGELOG.md)

---

![footer](https://jenly1314.github.io/page/footer.svg)

