# MVVMFrame

![Image](app/src/main/ic_launcher-web.png)

[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/MVVMFrame/master/app/release/app-release.apk)
[![MavenCentral](https://img.shields.io/maven-central/v/com.github.jenly1314/mvvmframe)](https://repo1.maven.org/maven2/com/github/jenly1314/mvvmframe)
[![JCenter](https://img.shields.io/badge/JCenter-2.0.0-46C018.svg)](https://bintray.com/beta/#/jenly/maven/mvvmframe)
[![JitPack](https://jitpack.io/v/jenly1314/MVVMFrame.svg)](https://jitpack.io/#jenly1314/MVVMFrame)
[![CI](https://travis-ci.org/jenly1314/MVVMFrame.svg?branch=master)](https://travis-ci.org/jenly1314/MVVMFrame)
[![CircleCI](https://circleci.com/gh/jenly1314/MVVMFrame.svg?style=svg)](https://circleci.com/gh/jenly1314/MVVMFrame)
[![API](https://img.shields.io/badge/API-16%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)
[![Blog](https://img.shields.io/badge/blog-Jenly-9933CC.svg)](https://jenly1314.github.io/)
[![QQGroup](https://img.shields.io/badge/QQGroup-20867961-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad)

MVVMFrame for Android 是一个基于Google官方推出的Architecture Components dependencies（现在叫JetPack）{ Lifecycle，LiveData，ViewModel，Room } 构建的快速开发框架。有了 **MVVMFrame** 的加持，从此构建一个 **MVVM** 模式的项目变得快捷简单。

## 架构
![Image](image/mvvm_architecture.jpg)

## [Android version](https://github.com/jenly1314/MVVMFrame/tree/android)

## 引入

> 由于2021年2月3日 **JFrog宣布将关闭Bintray和JCenter，计划在2022年2月完全关闭。** 所以后续版本不再发布至 **JCenter**

1. 在Project的 **build.gradle** 里面添加远程仓库  
          
```gradle
allprojects {
    repositories {
        //...
        mavenCentral()
    }
}
```

2. 在Module的 **build.gradle** 里面添加引入依赖项

#### **v2.x**（使用 **Hilt** 简化 **Dagger2** 依赖注入用法）
```gradle
//AndroidX 版本
implementation 'com.github.jenly1314:mvvmframe:2.1.0'

```


以前发布至JCenter的版本

#### **v2.0.0**（使用 **Hilt** 简化 **Dagger2** 依赖注入用法）
```gradle
//AndroidX 版本
implementation 'com.king.frame:mvvmframe:2.0.0'

```

#### **v1.x** 以前版本（使用 **Dagger2**）
```gradle
//AndroidX 版本
implementation 'com.king.frame:mvvmframe:1.1.4'

//Android Support版本
implementation 'com.king.frame:mvvmframe:1.0.2'
```

### **Dagger**和 **Room** 的相关注解处理器

   你需要引入下面的列出的编译时的注解处理器，用于自动生成相关代码。其它对应版本具体详情可查看 [Versions](https://github.com/jenly1314/MVVMFrame/releases)
  
   
#### **v2.x** 版本（**$versions** 相关可查看[Versions](versions.gradle)）

你需要在项目根目录的 **build.gradle** 文件中配置 **Hilt** 的插件路径：
```gradle
buildscript {
    ...
    dependencies {
        ...
        classpath "com.google.dagger:hilt-android-gradle-plugin:$versions.daggerHint"
    }
}
```
接下来，在 **app/build.gradle** 文件中，引入 **Hilt** 的插件和相关依赖：

```gradle
...
apply plugin: 'dagger.hilt.android.plugin'

dependencies{
    ...

    //AndroidX ------------------ MVVMFrame v2.x.x
    //lifecycle
    annotationProcessor "androidx.lifecycle:lifecycle-compiler:$versions.lifecycle"
    //room
    annotationProcessor "androidx.room:room-compiler:$versions.room"
    //hilt
    implementation "com.google.dagger:hilt-android:$versions.daggerHint"
    annotationProcessor "com.google.dagger:hilt-android-compiler:$versions.daggerHint"

//从2.1.0以后已移除
//    implementation "androidx.hilt:hilt-lifecycle-viewmodel:$versions.hilt"
//    annotationProcessor "androidx.hilt:hilt-compiler:$versions.hilt"
}

```

#### **v1.x** 以前版本，建议 [查看分支版本](https://github.com/jenly1314/MVVMFrame/tree/androidx)

在 **app/build.gradle** 文件中引入 **Dagger** 和 **Room** 相关依赖：
```gradle

dependencies{
    ...

    //AndroidX ------------------ MVVMFrame v1.1.4
    //dagger
    annotationProcessor 'com.google.dagger:dagger-android-processor:2.30.1'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.30.1'
    //room 
    annotationProcessor 'androidx.room:room-compiler:2.2.5'
}

```

```gradle

dependencies{
    ...

    // Android Support ------------------ MVVMFrame v1.0.2
    //dagger
    annotationProcessor 'com.google.dagger:dagger-android-processor:2.19'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.19'
    //room
    annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'
}

```
 如果你的项目使用的是 **Kotlin**，记得加上 **kotlin-kapt** 插件，并需使用 **kapt** 替代 **annotationProcessor** 

### MVVMFrame引入的库（具体对应版本请查看 [Versions](versions.gradle)）
```gradle
    //appcompat
    compileOnly deps.appcompat

    //retrofit
    api deps.retrofit.retrofit
    api deps.retrofit.gson
    api deps.retrofit.converter_gson

    //retrofit-helper
    api deps.jenly.retrofit_helper

    //lifecycle
    api deps.lifecycle.runtime
    api deps.lifecycle.extensions
    annotationProcessor deps.lifecycle.compiler

    //room
    api deps.room.runtime
    annotationProcessor deps.room.compiler

    //hilt
    compileOnly deps.dagger.hilt_android
    annotationProcessor  deps.dagger.hilt_android_compiler

    compileOnly deps.hilt.hilt_viewmodel
    annotationProcessor  deps.hilt.hilt_compiler

    //log
    api deps.timber

```


## 示例

集成步骤代码示例 （示例出自于[app](app)中）

**Step.1** 启用DataBinding，在你项目中的build.gradle的android{}中添加配置：

Android Studio 4.x 以后版本
```gradle
buildFeatures{
    dataBinding = true
}
```

Android Studio 4.x 以前版本
```gradle
dataBinding {
    enabled true
}

```


**Step.2** 使用JDK8编译（v1.1.2新增），在你项目中的build.gradle的android{}中添加配置：
```gradle
compileOptions {
    targetCompatibility JavaVersion.VERSION_1_8
    sourceCompatibility JavaVersion.VERSION_1_8
}

```

**Step.3** 自定义全局配置(继承MVVMFrame中的FrameConfigModule)（提示：如果你没有自定义配置的需求，可以直接忽略此步骤）
```java
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
                })
                .roomDatabaseOptions(new RoomDatabaseOptions<RoomDatabase>() {
                    @Override
                    public void applyOptions(RoomDatabase.Builder<RoomDatabase> builder) {
                        //TODO 配置RoomDatabase
                    }
                });
    }
}
```

**Step.4** 在你项目中的AndroidManifest.xml中通过配置meta-data来自定义全局配置（提示：如果你没有自定义配置的需求，可以直接忽略此步骤）
```xml
<!-- MVVMFrame 全局配置 -->
<meta-data android:name="com.king.mvvmframe.config.AppConfigModule"
           android:value="FrameConfigModule"/>
```

**Step.5** 关于Application

[**2.x版本**](app) 因为从**2.x**开始使用到了**Hilt**，所以你自定义的**Application**需加上 **@HiltAndroidApp** 注解，这是使用**Hilt**的一个必备前提。示例如下：
```java
   @HiltAndroidApp
   public class YourApplication extends Application {

   }
```

[**1.x版本**](https://github.com/jenly1314/MVVMFrame/tree/androidx) 将你项目的 **Application** 继承MVVMFrame中的 **BaseApplication**
```java
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
          //TODO 如果默认配置已经能满足你的需求，你不需要自定义配置，可以通过下面注释掉的方式设置 BaseUrl，从而可以省略掉 step3 , setp4 两个步骤。
//        RetrofitHelper.getInstance().setBaseUrl(baseUrl);
        super.onCreate();
        //开始构建项目时，DaggerApplicationComponent类可能不存在，你需要执行Make Project才能生成，Make Project快捷键 Ctrl + F9
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .appComponent(getAppComponent())
                .build();
        //注入
        appComponent.inject(this);

    }


}
```

## 其他

### 关于v2.x

因为**v2.x版本** 使用了 **Hilt** 的缘故，简化了之前 **Dagger2** 的用法，建议在新项目中使用。如果是从 **v1.x** 升级到 **v2.x**，集成步骤稍有变更，详情请查看 **Step.5**，并且可能还需要删除以前 **@Component**，**@Module**等注解桥接层相关的逻辑代码，因为从**v2.x**开始，这些桥接逻辑无需自己编写，全部交由 **Hilt** 处理。

### 关于使用 **Hilt**

**Hilt** 是JetPack中新增的一个依赖注入库，其基于**Dagger2**研发（后面统称为Dagger），但它不同于Dagger。对于Android开发者来说，Hilt可以说专门为Android 打造。

之前使用的**Dagger for Android**虽然也是针对于Android打造，也能通过 **@ContributesAndroidInjector** 来通过生成简化一部分样板代码，但是感觉还不够彻底。因为 **Component** 层相关的桥接还是要自己写。**Hilt**的诞生改善了这些问题。

**Hilt** 大幅简化了**Dagger** 的用法，使得我们不用通过 **@Component** 注解去编写桥接层的逻辑，但是也因此限定了注入功能只能从几个 **Android** 固定的入口点开始，

**Hilt** 一共支持 **6** 个入口点，分别是：

**Application**

**Activity**

**Fragment**

**View**

**Service**

**BroadcastReceiver**

其中，只有 **Application** 这个入口点是使用 **@HiltAndroidApp** 注解来声明，示例如下

 **Application** 示例
```java
   @HiltAndroidApp
   public class YourApplication extends Application {

   }
```

其他的所有入口点，都是用 **@AndroidEntryPoint** 注解来声明，示例如下

 **Activity** 示例
```java
   @AndroidEntryPoint
   public class YourActivity extends BaseActivity {

   }
```

 **Fragment** 示例
```java
   @AndroidEntryPoint
   public class YourFragment extends BaseFragment {

   }
```

 **Service** 示例
```java
   @AndroidEntryPoint
   public class YourService extends BaseService {

   }
```

 **BroadcastReceiver** 示例
```java
   @AndroidEntryPoint
   public class YourBroadcastReceiver extends BaseBroadcastReceiver {

   }
```

### 其它示例

 **BaseViewModel** 示例 （如果您继承使用了BaseViewModel或其子类，你需要参照如下方式在类上添加 **@HiltViewModel** 并在构造函数上添加 **@Inject** 注解）
```java
   @HiltViewModel
   public class YourViewModel extends BaseViewModel<YourModel> {
       @Inject
       public DataViewModel(@NonNull Application application, YourModel model) {
           super(application, model);
       }
   }
```

 **BaseModel** 示例 （如果您继承使用了BaseModel或其子类，你需要参照如下方式在构造函数上添加 **@Inject** 注解）
```java
   public class YourModel extends BaseModel {
       @Inject
       public BaseModel(IDataRepository dataRepository){
           super(dataRepository);
       }
   }
```

 如果使用的是 v2.0.0 版本 （使用 **androidx.hilt:hilt-lifecycle-viewmodel** 的方式）

 **BaseViewModel** 示例 （如果您继承使用了BaseViewModel或其子类，你需要参照如下方式在构造函数上添加 **@ViewModelInject** 注解）
```java
   public class YourViewModel extends BaseViewModel<YourModel> {
       @ViewModelInject
       public DataViewModel(@NonNull Application application, YourModel model) {
           super(application, model);
       }
   }
```



### 关于使用 **Dagger**

之所以特意说 **Dagger** 是因为**Dagger**的学习曲线相对陡峭一点，没那么容易理解。

1. 如果你对 **Dagger** 很了解，那么你将会更加轻松的去使用一些注入相关的骚操作。
> 因为 **MVVMFrame** 中使用到了很多 **Dagger** 注入相关的一些操作。所以会涉及**Dagger**相关技术知识。

但是并不意味着你一定要会使用 **Dagger**，才能使用**MVVMFrameComponent**。
> 如果你对 **Dagger** 并不熟悉，其实也是可以用的，因为使用 **Dagger** 全局注入主要都已经封装好了。你只需参照**Demo** 中的示例，照葫芦画瓢。
> 主要关注一些继承了**BaseActivity**，**BaseFragment**，**BaseViewModel**等相关类即可。

这里列一些主要的通用注入参照示例：（下面**Dagger**相关的示例仅适用于**v1.x**版本，因为**v2.x**已基于**Hilt**编写，简化了**Dagger**依赖注入桥接层相关逻辑）

直接或间接继承了 **BaseActivity** 的配置示例：
```java
/**
 * Activity模块统一管理：通过{@link ContributesAndroidInjector}方式注入，自动生成模块组件关联代码，减少手动编码
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = BaseActivitySubcomponent.class)
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}
```

直接或间接继承了 **BaseFragment** 的配置示例：
```java
/**
 * Fragment模块统一管理：通过{@link ContributesAndroidInjector}方式注入，自动生成模块组件关联代码，减少手动编码
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = BaseFragmentSubcomponent.class)
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

}
```

直接或间接继承了 **BaseViewModel** 的配置示例：
```java
/**
 * ViewModel模块统一管理：通过{@link Binds}和{@link ViewModelKey}绑定关联对应的ViewModel
 * ViewModelModule 例子
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
```

**ApplicationModule** 的配置示例
```java
/**
 * Application模块：为{@link ApplicationComponent}提供注入的各个模块
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(includes = {ViewModelFactoryModule.class,ViewModelModule.class,ActivityModule.class,FragmentModule.class})
public class ApplicationModule {

}
```

**ApplicationComponent** 的配置示例
```java
/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ApplicationScope
@Component(dependencies = AppComponent.class,modules = {ApplicationModule.class})
public interface ApplicationComponent {
    //指定你的 Application 继承类
    void inject(App app);
}
```

通过上面的通用配置注入你所需要的相关类之后，如果配置没什么问题，你只需 **执行Make Project** 一下，或通过 **Make Project** 快捷键 **Ctrl + F9** ，就可以自动生产相关代码。
比如通过 **ApplicationComponent** 生成的 **DaggerApplicationComponent** 类。

然后在你的 **Application** 集成类 **App** 中通过 **DaggerApplicationComponent** 构建 **ApplicationComponent**，然后注入即可。
```java
    //开始构建项目时，DaggerApplicationComponent类可能不存在，你需要执行Make Project才能生成，Make Project快捷键 Ctrl + F9
    ApplicationComponent appComponent = DaggerApplicationComponent.builder()
            .appComponent(getAppComponent())
            .build();
    //注入
    appComponent.inject(this);
```

你也可以直接查看[app](app)中的源码示例


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
>



更多使用详情，请查看[app](app)中的源码使用示例或直接查看[API帮助文档](https://jenly1314.github.io/projects/MVVMFrame/doc/)

### 相关开源项目
##### [KingWeather](https://github.com/jenly1314/KingWeather)  一款天气预报APP
##### [EasyChat](https://github.com/yetel/EasyChatAndroidClient) 一款即时通讯APP
##### [AppTemplate](https://github.com/jenly1314/AppTemplate) 一款基于**MVVMFrame**构建的App模板
##### [MVVMFrameComponent](https://github.com/jenly1314/MVVMFrameComponent) 一款基于**MVVMFrame**构建的组件化方案

## 压缩与混淆

 从 **Android Studio 3.3+** 之后，AS新增特性代码压缩工具 **R8** ，**R8** 旨在集成 **ProGuard** 和 **D8** 的功能。
 目前推荐第三方库都自己配置混淆规则，这样在混淆时，如果使用 **R8** ，则可以直接包含第三方依赖库的混淆规则，就不用繁琐的去配置每个依赖库的混淆规则。
 
 目前 **MVVFrame** 所有依赖混淆规则详情：[ProGuard rules](lib/proguard-rules.pro)

## 版本记录

#### v2.1.0：2021-4-28  (从v2.1.0开始不再发布至JCenter)
*  更新Hilt至v2.35
*  移除androidx.hilt:hilt-lifecycle-viewmodel [移除原因请查看Dagger v2.34更新说明](https://github.com/google/dagger/releases)
*  更新Lifecycle至v2.3.1
*  更新Room至v2.3.0
*  更新RetrofitHelper至v1.0.1

#### v2.0.0：2021-1-15
*  使用Hilt简化Dagger依赖注入用法

#### v1.1.4：2020-12-14
*  优化细节
*  更新Dagger至v2.30.1

#### v1.1.3：2020-6-1
*  支持配置多个BaseUrl，且支持动态改变（详情查看 [RetrofitHelper](https://github.com/jenly1314/RetrofitHelper)） 
*  对外暴露更多配置，（详情查看 FrameConfigModule）
*  优化细节
*  更新Retrofit至v2.9.0

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
如果你喜欢MVVMFrame，或感觉MVVMFrame帮助到了你，可以点右上角“Star”支持一下，你的支持就是我的动力，谢谢 :smiley:<p>
你也可以扫描下面的二维码，请作者喝杯咖啡 :coffee:
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

   GitHub: <a title="GitHub开源项目" href="https://github.com/jenly1314" target="_blank">jenly1314</a>
   
   Gitee: <a title="Gitee开源项目" href="https://gitee.com/jenly1314" target="_blank">jenly1314</a>

   加入QQ群: <a title="点击加入QQ群" href="http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad" target="_blank">20867961</a>
   <div>
       <img src="https://jenly1314.github.io/image/jenly666.png">
       <img src="https://jenly1314.github.io/image/qqgourp.png">
   </div>


   
