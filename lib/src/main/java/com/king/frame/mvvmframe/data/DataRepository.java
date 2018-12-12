package com.king.frame.mvvmframe.data;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.king.frame.mvvmframe.config.Constants;
import com.king.frame.mvvmframe.util.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import retrofit2.Retrofit;

/**
 * 统一管理数据业务层
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Singleton
public class DataRepository implements IDataRepository {

    @Inject
    Lazy<Retrofit> mRetrofit;

    @Inject
    Application mApplication;

    private LruCache<String,Object> mRetrofitServiceCache;
    private LruCache<String,Object> mRoomDatabaseCache;

    @Inject
    public DataRepository(){

    }

    /**
     * 提供上下文{@link Context}
     * @return {@link #mApplication}
     */
    @Override
    public Context getContext() {
        return mApplication;
    }

    /**
     * 传入Class 通过{@link retrofit2.Retrofit#create(Class)} 获得对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    @Override
    public <T> T getRetrofitService(@NonNull Class<T> service) {
        if(mRetrofitServiceCache == null){
            mRetrofitServiceCache = new LruCache<>(Constants.DEFAULT_RETROFIT_SERVICE_MAX_SIZE);
        }
        Preconditions.checkNotNull(mRetrofitServiceCache);

        T retrofitService = (T)mRetrofitServiceCache.get(service.getCanonicalName());
        if(retrofitService == null){
            synchronized (mRetrofitServiceCache) {
                if(retrofitService == null){
                    retrofitService = mRetrofit.get().create(service);
                    //缓存
                    mRetrofitServiceCache.put(service.getCanonicalName(),retrofitService);
                }

            }
        }

        return retrofitService;
    }

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param dbName 为{@code null}时，默认为{@link Constants#DEFAULT_DATABASE_NAME}
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    @Override
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database,@Nullable String dbName) {
        if(mRoomDatabaseCache == null){
            mRoomDatabaseCache = new LruCache<>(Constants.DEFAULT_ROOM_DATABASE_MAX_SIZE);
        }
        Preconditions.checkNotNull(mRoomDatabaseCache);

        T roomDatabase = (T)mRoomDatabaseCache.get(database.getCanonicalName());
        if(roomDatabase == null) {
            synchronized (mRoomDatabaseCache) {
                if (roomDatabase == null) {
                    RoomDatabase.Builder<T> builder = Room.databaseBuilder(getContext().getApplicationContext(), database, TextUtils.isEmpty(dbName) ? Constants.DEFAULT_DATABASE_NAME : dbName);
                    roomDatabase = builder.build();
                    //缓存
                    mRoomDatabaseCache.put(database.getCanonicalName(), roomDatabase);
                }
            }
        }
        return roomDatabase;
    }

}
