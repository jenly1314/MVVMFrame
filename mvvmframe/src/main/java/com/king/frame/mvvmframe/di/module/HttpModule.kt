package com.king.frame.mvvmframe.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.king.frame.mvvmframe.config.AppliesOptions
import com.king.frame.mvvmframe.config.AppliesOptions.GsonOptions
import com.king.frame.mvvmframe.config.AppliesOptions.OkHttpClientOptions
import com.king.frame.mvvmframe.config.AppliesOptions.RetrofitOptions
import com.king.frame.mvvmframe.config.Config
import com.king.retrofit.retrofithelper.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * Http相关注入
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@InstallIn(SingletonComponent::class)
@Module
object HttpModule {

    @Singleton
    @Provides
    fun provideRetrofit(builder: Retrofit.Builder, options: RetrofitOptions?): Retrofit {
        options?.applyOptions(builder)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder,
        config: Config,
        options: OkHttpClientOptions?
    ): OkHttpClient {
        options?.applyOptions(builder)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = config.httpLoggingLevel
        }
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideGson(builder: GsonBuilder, options: GsonOptions?): Gson {
        options?.applyOptions(builder)
        return builder.create()
    }

    @Singleton
    @Provides
    fun provideConfig(
        builder: Config.Builder,
        options: AppliesOptions.ConfigOptions?
    ): Config {
        options?.applyOptions(builder)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        httpUrl: HttpUrl,
        client: OkHttpClient,
        gson: Gson,
        config: Config
    ): Retrofit.Builder {
        val builder = Retrofit.Builder()
        builder.baseUrl(httpUrl)
            .client(client)
        if (config.isAddGsonConverterFactory) {
            builder.addConverterFactory(GsonConverterFactory.create(gson))
        }
        return builder
    }

    @Singleton
    @Provides
    fun provideClientBuilder(): OkHttpClient.Builder {
        return RetrofitHelper.getInstance().createClientBuilder()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): GsonBuilder {
        return GsonBuilder()
    }

    @Singleton
    @Provides
    fun provideConfigBuilder(): Config.Builder {
        return Config.Builder()
    }

    private const val TAG = "OkHttp"
}
