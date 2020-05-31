package com.king.frame.mvvmframe.di.module;


import com.king.frame.mvvmframe.config.AppliesOptions;
import com.king.frame.mvvmframe.util.Preconditions;
import com.king.retrofit.retrofithelper.RetrofitHelper;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.RoomDatabase;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module
public class ConfigModule {

    private HttpUrl mBaseUrl;

    private AppliesOptions.RetrofitOptions mRetrofitOptions;

    private AppliesOptions.OkHttpClientOptions mOkHttpClientOptions;

    private AppliesOptions.GsonOptions mGsonOptions;

    private AppliesOptions.InterceptorConfigOptions mInterceptorConfigOptions;

    private AppliesOptions.RoomDatabaseOptions mRoomDatabaseOptions;

    private ConfigModule(Builder builder){
        this.mBaseUrl = builder.baseUrl;
        this.mRetrofitOptions = builder.retrofitOptions;
        this.mOkHttpClientOptions = builder.okHttpClientOptions;
        this.mGsonOptions = builder.gsonOptions;
        this.mInterceptorConfigOptions = builder.interceptorConfigOptions;
        this.mRoomDatabaseOptions = builder.roomDatabaseOptions;
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl(){
        if(mBaseUrl == null){//如果 mBaseUrl 为空表示没有在自定义配置 FrameConfigModule 中配过 BaseUrl
            //尝试去 RetrofitHelper 中取一次 BaseUrl，这里相当于多支持一种配置 BaseUrl 的方式
            mBaseUrl = RetrofitHelper.getInstance().getBaseUrl();
        }
        //再次检测 mBaseUrl 是否为空，如果依旧为空，表示两种配置方式都没有配置过，则直接抛出异常
        Preconditions.checkNotNull(mBaseUrl,"Base URL required.");
        return mBaseUrl;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.RetrofitOptions provideRetrofitOptions(){
        return mRetrofitOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.OkHttpClientOptions provideOkHttpClientOptions(){
        return mOkHttpClientOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.GsonOptions provideGsonOptions(){
        return mGsonOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.InterceptorConfigOptions provideInterceptorConfigOptions(){
        return mInterceptorConfigOptions;
    }

    @Singleton
    @Provides
    AppliesOptions.RoomDatabaseOptions provideRoomDatabaseOptions(){
        if(mRoomDatabaseOptions == null){
            mRoomDatabaseOptions = new AppliesOptions.RoomDatabaseOptions() {
                @Override
                public void applyOptions(RoomDatabase.Builder builder) {

                }
            };
        }
        return mRoomDatabaseOptions;
    }


    public static final class Builder {

        private HttpUrl baseUrl;

        private AppliesOptions.RetrofitOptions retrofitOptions;

        private AppliesOptions.OkHttpClientOptions okHttpClientOptions;

        private AppliesOptions.GsonOptions gsonOptions;

        private AppliesOptions.InterceptorConfigOptions interceptorConfigOptions;

        private AppliesOptions.RoomDatabaseOptions roomDatabaseOptions;

        public Builder(){

        }

        public Builder baseUrl(@NonNull String baseUrl) {
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(@NonNull HttpUrl baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder retrofitOptions(AppliesOptions.RetrofitOptions retrofitOptions) {
            this.retrofitOptions = retrofitOptions;
            return this;
        }

        public Builder okHttpClientOptions(AppliesOptions.OkHttpClientOptions okHttpClientOptions) {
            this.okHttpClientOptions = okHttpClientOptions;
            return this;
        }

        public Builder gsonOptions(AppliesOptions.GsonOptions gsonOptions){
            this.gsonOptions = gsonOptions;
            return this;
        }

        public Builder interceptorConfigOptions(AppliesOptions.InterceptorConfigOptions interceptorConfigOptions){
            this.interceptorConfigOptions = interceptorConfigOptions;
            return this;
        }

        public Builder roomDatabaseOptions(AppliesOptions.RoomDatabaseOptions<? extends RoomDatabase> roomDatabaseOptions){
            this.roomDatabaseOptions = roomDatabaseOptions;
            return this;
        }

        public ConfigModule build(){
            return new ConfigModule(this);
        }
    }


}
