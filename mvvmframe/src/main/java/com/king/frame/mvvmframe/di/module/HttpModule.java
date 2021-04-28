package com.king.frame.mvvmframe.di.module;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.king.frame.mvvmframe.config.AppliesOptions;
import com.king.frame.mvvmframe.http.InterceptorConfig;
import com.king.frame.mvvmframe.http.interceptor.LogInterceptor;
import com.king.retrofit.retrofithelper.RetrofitHelper;

import javax.inject.Singleton;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@InstallIn(SingletonComponent.class)
@Module
public class HttpModule {


    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder,@Nullable AppliesOptions.RetrofitOptions options){
        if(options != null){
            options.applyOptions(builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder,@Nullable AppliesOptions.OkHttpClientOptions options){
        if(options != null) {
            options.applyOptions(builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    Gson provideGson(GsonBuilder builder,@Nullable AppliesOptions.GsonOptions options){
        if(options != null){
            options.applyOptions(builder);
        }
        return builder.create();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(HttpUrl httpUrl, OkHttpClient client, Gson gson,InterceptorConfig config) {
        Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(httpUrl)
                    .client(client);
            if(config.isAddGsonConverterFactory()){
                builder.addConverterFactory(GsonConverterFactory.create(gson));
            }

        return builder;
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder(InterceptorConfig config) {
        OkHttpClient.Builder builder = RetrofitHelper.getInstance().createClientBuilder();

        if(config.isAddLog()){
            builder.addInterceptor(new LogInterceptor());
        }

        return builder;
    }

    @Singleton
    @Provides
    GsonBuilder provideGsonBuilder(){
        return new GsonBuilder();
    }

    @Singleton
    @Provides
    InterceptorConfig provideInterceptorConfig(InterceptorConfig.Builder builder, @Nullable AppliesOptions.InterceptorConfigOptions options){
        if(options != null){
            options.applyOptions(builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    InterceptorConfig.Builder provideInterceptorConfigBuilder(){
        return InterceptorConfig.newBuilder();
    }


}
