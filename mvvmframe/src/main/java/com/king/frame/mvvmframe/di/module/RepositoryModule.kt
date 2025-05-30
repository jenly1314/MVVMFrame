package com.king.frame.mvvmframe.di.module

import com.king.frame.mvvmframe.data.DataRepository
import com.king.frame.mvvmframe.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * 仓库注入
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Deprecated(
    "This Class is deprecated. Use DataModule instead",
    replaceWith = ReplaceWith(
        expression = "DataModule",
        imports = ["com.king.frame.mvvmframe.di.module.DataModule"]
    )
)
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    /**
     * 绑定Repository
     */
    @Binds
    abstract fun bindRepository(dataRepository: DataRepository): Repository
}
