package com.king.frame.mvvmframe.config

import androidx.collection.LruCache
import androidx.room.RoomDatabase
import com.king.frame.mvvmframe.data.datasource.DataSource
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class Config(builder: Builder) {

    internal val httpLoggingLevel: Level
    internal val isAddGsonConverterFactory: Boolean
    internal val retrofitServiceMaxCacheSize: Int
    internal val roomDatabaseMaxCacheSize: Int

    init {
        isAddGsonConverterFactory = builder.isAddGsonConverterFactory
        httpLoggingLevel = builder.httpLoggingLevel
        retrofitServiceMaxCacheSize = builder.retrofitServiceMaxCacheSize
        roomDatabaseMaxCacheSize = builder.roomDatabaseMaxCacheSize
    }

    /**
     * [Config.Builder]
     */
    @Suppress("unused")
    class Builder {

        internal var isAddGsonConverterFactory: Boolean = true
        internal var httpLoggingLevel: Level = Level.NONE
        internal var retrofitServiceMaxCacheSize = DEFAULT_RETROFIT_SERVICE_MAX_SIZE
        internal var roomDatabaseMaxCacheSize = DEFAULT_ROOM_DATABASE_MAX_SIZE

        /**
         * 配置是否添加 [GsonConverterFactory]；默认为：true
         */
        fun addGsonConverterFactory(addGsonConverterFactory: Boolean): Builder {
            this.isAddGsonConverterFactory = addGsonConverterFactory
            return this
        }

        /**
         * 配置 http日志级别 [HttpLoggingInterceptor]；默认为：[Level.BASIC]
         */
        fun httpLoggingLevel(level: Level): Builder {
            this.httpLoggingLevel = level
            return this
        }

        /**
         *  [DataSource]中通过[DataSource.getRetrofitService]获取 service 实例的最大可缓存数量，
         *  采用[LruCache]缓存策略；默认值为：[DEFAULT_RETROFIT_SERVICE_MAX_SIZE]
         */
        fun retrofitServiceMaxCacheSize(size: Int): Builder {
            this.retrofitServiceMaxCacheSize = size
            return this
        }

        /**
         * [DataSource]中 通过 [DataSource.getRoomDatabase] 获取 [RoomDatabase] 实例的最大缓存数量，
         * 采用[LruCache]缓存策略；默认值为：[DEFAULT_ROOM_DATABASE_MAX_SIZE]
         */
        fun roomDatabaseMaxCacheSize(size: Int): Builder {
            this.roomDatabaseMaxCacheSize = size
            return this
        }

        /**
         * 构建[Config]
         */
        fun build(): Config {
            return Config(this)
        }

        override fun toString(): String {
            return "Builder(isAddGsonConverterFactory=$isAddGsonConverterFactory, " +
                "httpLoggingLevel=$httpLoggingLevel, " +
                "retrofitServiceMaxCacheSize=$retrofitServiceMaxCacheSize, " +
                "roomDatabaseMaxCacheSize=$roomDatabaseMaxCacheSize)"
        }

    }

    companion object {

        internal const val DEFAULT_RETROFIT_SERVICE_MAX_SIZE = 60

        internal const val DEFAULT_ROOM_DATABASE_MAX_SIZE = 10
    }
}
