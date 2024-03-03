package com.king.frame.mvvmframe.data

import android.content.Context
import androidx.collection.LruCache
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.king.frame.mvvmframe.config.AppliesOptions.RoomDatabaseOptions
import com.king.frame.mvvmframe.config.Config
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 数据仓库
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Singleton
open class DataRepository @Inject constructor() : Repository {

    @ApplicationContext
    @Inject
    internal lateinit var mContext: Context

    @Inject
    internal lateinit var mRetrofit: Retrofit

    @Inject
    internal lateinit var mConfig: Config

    @Inject
    internal lateinit var mRoomDatabaseOptions: RoomDatabaseOptions

    /**
     * 缓存 Retrofit Service
     */
    private val mRetrofitServiceCache: LruCache<Class<*>, Any> by lazy {
        LruCache(mConfig.retrofitServiceMaxCacheSize)
    }

    /**
     * 缓存 RoomDatabase
     */
    private val mRoomDatabaseCache: LruCache<Class<*>, Any> by lazy {
        LruCache(mConfig.roomDatabaseMaxCacheSize)
    }

    /**
     * 传入API接口类的Class，通过[Retrofit.create] 获得对应的Class
     *
     * @param service [Retrofit] 对应的API接口定义
     * @param T     泛型[T]为具体的接口对象类型
     * @return [Retrofit.create]
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T> getRetrofitService(service: Class<T>): T {
        var retrofitService = mRetrofitServiceCache[service] as? T
        if (retrofitService == null) {
            synchronized(mRetrofitServiceCache) {
                if (retrofitService == null) {
                    retrofitService = mRetrofit.create(service)
                    // 缓存
                    mRetrofitServiceCache.put(service, retrofitService!!)
                }
            }
        }
        return retrofitService!!
    }

    /**
     * 传入数据库类的Class，通过[Room.databaseBuilder]获得对应的[RoomDatabase] 实现
     *
     * @param database 数据库抽象类；用注解[Database]注释并继承[RoomDatabase]的抽象类
     * @param T      泛型[T]为数据库对象的类型
     */
    override fun <T : RoomDatabase> getRoomDatabase(database: Class<T>): T {
        return getRoomDatabase(database, null)
    }

    /**
     * 传入数据库类的Class，通过[Room.databaseBuilder]获得对应的[RoomDatabase] 实现
     *
     * @param database 数据库抽象类；用注解[Database]注释并继承[RoomDatabase]的抽象类
     * @param dbName   数据库文件名称，当传值为空时，则文件名默认为[DataRepository.DEFAULT_DATABASE_NAME]
     * @param T      泛型[T]为数据库对象的类型
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : RoomDatabase> getRoomDatabase(database: Class<T>, dbName: String?): T {
        var roomDatabase = mRoomDatabaseCache[database] as? T
        if (roomDatabase == null) {
            synchronized(mRoomDatabaseCache) {
                if (roomDatabase == null) {
                    val builder: RoomDatabase.Builder<T> = databaseBuilder(
                        mContext,
                        database,
                        dbName?.takeIf { it.isNotBlank() } ?: DEFAULT_DATABASE_NAME
                    )
                    mRoomDatabaseOptions.applyOptions(builder)

                    roomDatabase = builder.build().also {
                        // 缓存
                        mRoomDatabaseCache.put(database, it)
                    }
                }
            }
        }
        return roomDatabase!!
    }

    companion object {
        private const val DEFAULT_DATABASE_NAME = "room.dp"
    }
}