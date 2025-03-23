package com.king.mvvmframe.config

import android.content.Context
import androidx.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.king.frame.mvvmframe.config.AppliesOptions
import com.king.frame.mvvmframe.config.AppliesOptions.GsonOptions
import com.king.frame.mvvmframe.config.AppliesOptions.OkHttpClientOptions
import com.king.frame.mvvmframe.config.AppliesOptions.RetrofitOptions
import com.king.frame.mvvmframe.config.AppliesOptions.RoomDatabaseOptions
import com.king.frame.mvvmframe.config.Config
import com.king.frame.mvvmframe.config.FrameConfigModule
import com.king.frame.mvvmframe.di.module.ConfigModule
import com.king.mvvmframe.BuildConfig
import com.king.mvvmframe.constant.Constants
import com.king.retrofit.retrofithelper.RetrofitHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * 全局配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class AppConfigModule : FrameConfigModule() {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {
        /**
         * > 目前通过设置 BaseUrl 的入口主要有两种：
         * >> 1.一种是通过在 Manifest 中配置 meta-data 的来自定义 FrameConfigModule,在里面 通过 [ConfigModule.Builder.baseUrl]来配置 BaseUrl。（一次设置，全局配置）
         * >
         * >> 2.一种就是通过RetrofitHelper [RetrofitHelper.setBaseUrl] 或 [RetrofitHelper.setBaseUrl] 来配置 BaseUrl。（可多次设置，动态全局配置，有前提条件）
         * >
         * > 以上两种配置 BaseUrl 的方式都可以达到目的。但是你可以根据不同的场景选择不同的配置方式。
         * >
         * > 主要场景与选择如下：
         * >
         * >> 一般场景：对于只使用单个不变的 BaseUrl的
         * >>>     场景1:如果本库的默认已满足你的需求，无需额外自定义配置的。
         * >          选择：建议你直接使用 [RetrofitHelper.setBaseUrl] 或 [RetrofitHelper.setBaseUrl] 来初始化 BaseUrl，切记在框架配置初始化 BaseUrl之前，建议在你自定义的 [Application.onCreate]中初始化。
         * >
         * >>>     场景2:如果本库的默认配置不满足你的需求，你需要自定义一些配置的。（比如需要使用 RxJava相关）
         * >          选择：建议你在自定义配置中通过 [ConfigModule.Builder.baseUrl] 来初始化 BaseUrl。
         * >
         * >> 二般场景：对于只使用单个 BaseUrl 但是，BaseUrl中途会变动的。
         * >>>     场景3：和一般场景一样，也能分两种，所以选择也和一般场景也可以是一样的。
         * >          选择：两种选择都行，但当 BaseUrl需要中途变动时，还需将 [RetrofitHelper.setDynamicDomain] 设置为 `true` 才能支持动态改变 BaseUrl。
         * >
         * >> 特殊场景：对于支持多个 BaseUrl 且支持动态可变的。
         * >>>        选择：这个场景的选择，主要涉及到另外的方法，请查看 [RetrofitHelper.putDomain] 和 [RetrofitHelper.putDomain]相关详情
         * >
         */
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
                    builder.fallbackToDestructiveMigration()
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
