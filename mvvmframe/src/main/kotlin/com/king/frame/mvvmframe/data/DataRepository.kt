package com.king.frame.mvvmframe.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.frame.mvvmframe.data.datasource.DefaultDataSource
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Retrofit

/**
 * 数据仓库
 *
 * -------------------------------------------------
 *
 * 此类从v3.1.0版本开始已标记为废弃，后续版本可能会移除；
 *
 * 为了更适用于大型项目，所以细化了分层粒度；新增了数据源层：[DataSource]，然后将本类中原来提供获取
 * 的[getRetrofitService]和[getRoomDatabase]已迁移到[DataSource]，迁移后分层看起来更清晰。
 *
 * 后续获取[getRetrofitService]和[getRoomDatabase]请使用[DataSource]；通常在定义的Repository中
 * 持有[DataSource]即可。
 *
 * -------------------------------------------------
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Deprecated(
    "This Class is deprecated. Use DataSourceImpl instead",
    replaceWith = ReplaceWith(
        expression = "DataSourceImpl",
        imports = ["com.king.frame.mvvmframe.data.datasource.DataSourceImpl"]
    )
)
@Singleton
open class DataRepository @Inject constructor(private val dataSource: DataSource) : Repository {

    /**
     * 传入API接口类的Class，通过[Retrofit.create] 获得对应的Class
     *
     * @param service [Retrofit] 对应的API接口定义
     * @param T     泛型[T]为具体的接口对象类型
     * @return [Retrofit.create]
     */
    override fun <T> getRetrofitService(service: Class<T>): T {
        return dataSource.getRetrofitService(service)
    }

    /**
     * 传入数据库类的Class，通过[Room.databaseBuilder]获得对应的[RoomDatabase] 实现
     *
     * @param database 数据库抽象类；用注解[Database]注释并继承[RoomDatabase]的抽象类
     * @param T      泛型[T]为数据库对象的类型
     */
    override fun <T : RoomDatabase> getRoomDatabase(database: Class<T>): T {
        return dataSource.getRoomDatabase(database)
    }

    /**
     * 传入数据库类的Class，通过[Room.databaseBuilder]获得对应的[RoomDatabase] 实现
     *
     * @param database 数据库抽象类；用注解[Database]注释并继承[RoomDatabase]的抽象类
     * @param dbName   数据库文件名称，当传值为空时，则文件名默认为[DefaultDataSource.DEFAULT_DATABASE_NAME]
     * @param T      泛型[T]为数据库对象的类型
     */
    override fun <T : RoomDatabase> getRoomDatabase(database: Class<T>, dbName: String?): T {
        return dataSource.getRoomDatabase(database, dbName)
    }

}
