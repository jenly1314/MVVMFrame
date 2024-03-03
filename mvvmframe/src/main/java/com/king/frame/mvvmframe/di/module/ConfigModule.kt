package com.king.frame.mvvmframe.di.module

import android.content.Context
import androidx.room.RoomDatabase
import com.king.frame.mvvmframe.config.AppliesOptions.ConfigOptions
import com.king.frame.mvvmframe.config.AppliesOptions.GsonOptions
import com.king.frame.mvvmframe.config.AppliesOptions.OkHttpClientOptions
import com.king.frame.mvvmframe.config.AppliesOptions.RetrofitOptions
import com.king.frame.mvvmframe.config.AppliesOptions.RoomDatabaseOptions
import com.king.frame.mvvmframe.config.ManifestParser
import com.king.retrofit.retrofithelper.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.util.Objects
import javax.inject.Singleton

/**
 * 配置注入
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@InstallIn(SingletonComponent::class)
@Module
object ConfigModule {
    @Singleton
    @Provides
    fun provideBaseUrl(builder: Builder): HttpUrl {
        var baseUrl = builder.baseUrl
        if (baseUrl == null) { // 如果 mBaseUrl 为空表示没有在自定义配置 FrameConfigModule 中配过 BaseUrl
            // 尝试去 RetrofitHelper 中取一次 BaseUrl，这里相当于多支持一种配置 BaseUrl 的方式
            baseUrl = RetrofitHelper.getInstance().baseUrl
        }
        // 再次检测 mBaseUrl 是否为空，如果依旧为空，表示两种配置方式都没有配置过，则直接抛出异常
        Objects.requireNonNull(baseUrl, "baseUrl == null")
        return baseUrl!!
    }

    @Singleton
    @Provides
    fun provideRetrofitOptions(builder: Builder): RetrofitOptions? {
        return builder.retrofitOptions
    }

    @Singleton
    @Provides
    fun provideOkHttpClientOptions(builder: Builder): OkHttpClientOptions? {
        return builder.okHttpClientOptions
    }

    @Singleton
    @Provides
    fun provideGsonOptions(builder: Builder): GsonOptions? {
        return builder.gsonOptions
    }

    @Singleton
    @Provides
    fun provideConfigOptions(builder: Builder): ConfigOptions? {
        return builder.configOptions
    }

    @Singleton
    @Provides
    fun provideRoomDatabaseOptions(builder: Builder): RoomDatabaseOptions {
        return builder.roomDatabaseOptions ?: object : RoomDatabaseOptions {
            override fun applyOptions(builder: RoomDatabase.Builder<out RoomDatabase>) {

            }
        }
    }

    @Singleton
    @Provides
    fun provideConfigModuleBuilder(@ApplicationContext context: Context): Builder {
        val builder = Builder()
        // 解析配置
        val modules = ManifestParser(context).parse()
        // 遍历配置
        for (configModule in modules) {
            // 如果启用则申请配置参数
            if (configModule.isManifestParsingEnabled()) {
                configModule.applyOptions(context, builder)
            }
        }
        return builder
    }

    class Builder {
        internal var baseUrl: HttpUrl? = null
        internal var retrofitOptions: RetrofitOptions? = null
        internal var okHttpClientOptions: OkHttpClientOptions? = null
        internal var gsonOptions: GsonOptions? = null
        internal var configOptions: ConfigOptions? = null
        internal var roomDatabaseOptions: RoomDatabaseOptions? = null
        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl.toHttpUrl()
            return this
        }

        fun baseUrl(baseUrl: HttpUrl): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun retrofitOptions(retrofitOptions: RetrofitOptions): Builder {
            this.retrofitOptions = retrofitOptions
            return this
        }

        fun okHttpClientOptions(okHttpClientOptions: OkHttpClientOptions): Builder {
            this.okHttpClientOptions = okHttpClientOptions
            return this
        }

        fun gsonOptions(gsonOptions: GsonOptions): Builder {
            this.gsonOptions = gsonOptions
            return this
        }

        fun configOptions(configOptions: ConfigOptions): Builder {
            this.configOptions = configOptions
            return this
        }

        fun roomDatabaseOptions(roomDatabaseOptions: RoomDatabaseOptions): Builder {
            this.roomDatabaseOptions = roomDatabaseOptions
            return this
        }
    }

}