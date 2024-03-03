package com.king.frame.mvvmframe.config

import android.content.Context
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.king.frame.mvvmframe.di.module.ConfigModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * MVVMFrame全局配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface AppliesOptions {
    /**
     * 为框架提供一些配置参数入口
     * @param context
     * @param builder
     */
    fun applyOptions(context: Context, builder: ConfigModule.Builder)

    /**
     * 为框架中的[Retrofit]提供配置参数入口
     */
    interface RetrofitOptions {
        fun applyOptions(builder: Retrofit.Builder)
    }

    /**
     * 为框架中的[OkHttpClient]提供配置参数入口
     */
    interface OkHttpClientOptions {
        fun applyOptions(builder: OkHttpClient.Builder)
    }

    /**
     * 为框架中的[Gson]提供配置参数入口
     */
    interface GsonOptions {
        fun applyOptions(builder: GsonBuilder)
    }

    /**
     * 为框架中的[Config]提供配置参数入口
     */
    interface ConfigOptions {
        fun applyOptions(builder: Config.Builder)
    }

    /**
     * 为框架中的[RoomDatabase]提供配置参数入口
     */
    interface RoomDatabaseOptions {
        fun applyOptions(builder: RoomDatabase.Builder<out RoomDatabase>)
    }
}