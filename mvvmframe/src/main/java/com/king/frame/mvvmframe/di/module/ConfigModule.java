package com.king.frame.mvvmframe.di.module;


import android.content.Context;

import com.king.frame.mvvmframe.config.AppliesOptions;
import com.king.frame.mvvmframe.config.FrameConfigModule;
import com.king.frame.mvvmframe.config.ManifestParser;
import com.king.frame.mvvmframe.util.Preconditions;
import com.king.retrofit.retrofithelper.RetrofitHelper;

import java.util.List;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.RoomDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@InstallIn(SingletonComponent.class)
@Module
public class ConfigModule {


    @Singleton
    @Provides
    HttpUrl provideBaseUrl(@NonNull Builder builder){
        HttpUrl baseUrl = builder.baseUrl;
        if(baseUrl == null){//如果 mBaseUrl 为空表示没有在自定义配置 FrameConfigModule 中配过 BaseUrl
            //尝试去 RetrofitHelper 中取一次 BaseUrl，这里相当于多支持一种配置 BaseUrl 的方式
            baseUrl = RetrofitHelper.getInstance().getBaseUrl();
        }
        //再次检测 mBaseUrl 是否为空，如果依旧为空，表示两种配置方式都没有配置过，则直接抛出异常
        Preconditions.checkNotNull(baseUrl,"Base URL required.");
        return baseUrl;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.RetrofitOptions provideRetrofitOptions(@NonNull Builder builder){
        return builder.retrofitOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.OkHttpClientOptions provideOkHttpClientOptions(@NonNull Builder builder){
        return builder.okHttpClientOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.GsonOptions provideGsonOptions(@NonNull Builder builder){
        return builder.gsonOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.InterceptorConfigOptions provideInterceptorConfigOptions(@NonNull Builder builder){
        return builder.interceptorConfigOptions;
    }

    @Singleton
    @Provides
    AppliesOptions.RoomDatabaseOptions provideRoomDatabaseOptions(@NonNull Builder builder){
        if(builder.roomDatabaseOptions != null){
            return builder.roomDatabaseOptions;
        }
        return it -> {};
    }

    @Singleton
    @Provides
    Builder provideConfigModuleBuilder(@ApplicationContext Context context){
        ConfigModule.Builder builder = new ConfigModule.Builder();
        //解析配置
        List<FrameConfigModule> modules = new ManifestParser(context).parse();
        //遍历配置
        for (FrameConfigModule configModule: modules){
            //如果启用则申请配置参数
            if(configModule.isManifestParsingEnabled()){
                configModule.applyOptions(context,builder);
            }
        }
        return builder;
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

    }


}
