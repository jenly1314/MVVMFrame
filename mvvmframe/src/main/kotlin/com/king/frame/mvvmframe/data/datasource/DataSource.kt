package com.king.frame.mvvmframe.data.datasource

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import retrofit2.Retrofit

/**
 * 数据源；默认实现请查看：[DefaultDataSource]
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface DataSource {

    /**
     * 传入API接口类的Class，通过[Retrofit.create] 获得对应的Class
     *
     * @param service [Retrofit] 对应的API接口定义
     * @param T     泛型[T]为具体的接口对象类型
     * @return [Retrofit.create]
     */
    fun <T> getRetrofitService(service: Class<T>): T

    /**
     * 传入数据库类的Class，通过[Room.databaseBuilder]获得对应的[RoomDatabase] 实现
     *
     * @param database 数据库抽象类；用注解[Database]注释并继承[RoomDatabase]的抽象类
     * @param T      泛型[T]为数据库对象的类型
     */
    fun <T : RoomDatabase> getRoomDatabase(database: Class<T>): T

    /**
     * 传入数据库类的Class，通过[Room.databaseBuilder]获得对应的[RoomDatabase] 实现
     *
     * @param database 数据库抽象类；用注解[Database]注释并继承[RoomDatabase]的抽象类
     * @param dbName   数据库文件名称
     * @param T      泛型[T]为数据库对象的类型
     */
    fun <T : RoomDatabase> getRoomDatabase(database: Class<T>, dbName: String?): T

}
