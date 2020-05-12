package com.king.frame.mvvmframe.di.module;


import com.king.frame.mvvmframe.config.AppliesOptions;
import com.king.frame.mvvmframe.util.Preconditions;

import javax.inject.Singleton;

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

    private AppliesOptions.RoomDatabaseOptions mRoomDatabaseOptions;

    private ConfigModule(Builder builder){
        this.mBaseUrl = builder.baseUrl;
        this.mRetrofitOptions = builder.retrofitOptions;
        this.mOkHttpClientOptions = builder.okHttpClientOptions;
        this.mGsonOptions = builder.gsonOptions;
        this.mRoomDatabaseOptions = builder.roomDatabaseOptions;
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl(){
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

        private AppliesOptions.RoomDatabaseOptions roomDatabaseOptions;

        public Builder(){

        }

        public Builder baseUrl(String baseUrl) {
            Preconditions.checkNotNull(baseUrl, "baseUrl == null");
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(HttpUrl baseUrl){
            Preconditions.checkNotNull(baseUrl, "baseUrl == null");
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

        public Builder roomDatabaseOptions(AppliesOptions.RoomDatabaseOptions<? extends RoomDatabase> roomDatabaseOptions){
            this.roomDatabaseOptions = roomDatabaseOptions;
            return this;
        }

        public ConfigModule build(){
            return new ConfigModule(this);
        }
    }


}
