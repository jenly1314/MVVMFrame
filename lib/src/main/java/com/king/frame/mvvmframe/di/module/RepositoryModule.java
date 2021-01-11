package com.king.frame.mvvmframe.di.module;

import com.king.frame.mvvmframe.data.DataRepository;
import com.king.frame.mvvmframe.data.IDataRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@InstallIn(SingletonComponent.class)
@Module
public abstract class RepositoryModule {

    @Binds
    abstract IDataRepository bindDataRepository(DataRepository dataRepository);
}
