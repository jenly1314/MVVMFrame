package com.king.frame.mvvmframe.data;

import android.content.Context;

import java.lang.Class;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * 统一管理数据业务层，实现类见{@link DataRepository}
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface IDataRepository {
    /**
     * 提供上下文{@link Context}
     * @return {@lik Context}
     */
    Context getContext();
    /**
     * 传入Class 通过{@link retrofit2.Retrofit#create(Class)} 获得对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    <T> T getRetrofitService(@NonNull Class<T> service);

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param dbName
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database, @Nullable String dbName);

}