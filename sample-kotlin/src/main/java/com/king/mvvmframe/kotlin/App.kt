package com.king.mvvmframe.kotlin

import android.app.Application
import com.king.mvvmframe.kotlin.app.Constants
import com.king.retrofit.retrofithelper.RetrofitHelper
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * 你需要参照如下方式添加@HiltAndroidApp注解
 *
 * @example Application
 * //-------------------------
 *    @HiltAndroidApp
 *    public class YourApplication extends Application {
 *
 *    }
 * //-------------------------
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()

        /**
         * > 目前通过设置 BaseUrl 的入口主要有两种：
         * >> 1.一种是通过在 Manifest 中配置 meta-data 的来自定义 FrameConfigModule,在里面 通过 {@link ConfigModule.Builder#baseUrl(String)}来配置 BaseUrl。（一次设置，全局配置）
         * >
         * >> 2.一种就是通过RetrofitHelper {@link RetrofitHelper#setBaseUrl(String)} 或 {@link RetrofitHelper#setBaseUrl(HttpUrl)} 来配置 BaseUrl。（可多次设置，动态全局配置，有前提条件）
         * >
         * > 以上两种配置 BaseUrl 的方式都可以达到目的。但是你可以根据不同的场景选择不同的配置方式。
         * >
         * > 主要场景与选择如下：
         * >
         * >> 一般场景：对于只使用单个不变的 BaseUrl的
         * >>>     场景1:如果本库的默认已满足你的需求，无需额外自定义配置的。
         * >          选择：建议你直接使用 {@link RetrofitHelper#setBaseUrl(String)} 或 {@link RetrofitHelper#setBaseUrl(HttpUrl)} 来初始化 BaseUrl，切记在框架配置初始化之前，即你的 {@link Application#onCreate()}的父类onCreate之前设置。
         * >
         * >>>     场景2:如果本库的默认配置不满足你的需求，你需要自定义一些配置的。（比如需要使用 RxJava相关）
         * >          选择：建议你在自定义配置中通过 {@link ConfigModule.Builder#baseUrl(String)} 来初始化 BaseUrl。
         * >
         * >> 二般场景：对于只使用单个 BaseUrl 但是，BaseUrl中途会变动的。
         * >>>     场景3：和一般场景一样，也能分两种，所以选择也和一般场景也可以是一样的。
         * >          选择：两种选择都行，但当 BaseUrl需要中途变动时，还需将 {@link RetrofitHelper#setDynamicDomain(boolean)} 设置为 {@code true} 才能支持动态改变 BaseUrl。
         * >
         * >> 特殊场景：对于支持多个 BaseUrl 且支持动态可变的。
         * >>>        选择：这个场景的选择，主要涉及到另外的方法，请查看 {@link RetrofitHelper#putDomain(String, String)} 和 {@link RetrofitHelper#putDomain(String, HttpUrl)}相关详情
         * >
         */
        //通过第二种方式初始化BaseUrl
        RetrofitHelper.getInstance().setBaseUrl(Constants.BASE_URL)
    }

    private fun initLogger() {
        //初始化日志打印
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .methodOffset(5)
                .tag(Constants.TAG)
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    Logger.log(priority, tag, message, t)
                }
            }
        })
    }
}