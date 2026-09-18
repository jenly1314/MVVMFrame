package com.king.frame.mvvmframe.di.module

import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.frame.mvvmframe.data.datasource.DefaultDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 数据源 注入
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    /**
     * 绑定DataSource
     */
    @Singleton
    @Binds
    abstract fun bindDataSource(datasource: DefaultDataSource): DataSource
}
