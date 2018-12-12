package com.king.frame.mvvmframe.di.module;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.king.frame.mvvmframe.config.AppliesOptions;
import com.king.frame.mvvmframe.config.Constants;
import com.king.frame.mvvmframe.http.interceptor.LogInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module
public class HttpModule {


    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder, HttpUrl httpUrl, @Nullable AppliesOptions.RetrofitOptions options, OkHttpClient client,Gson gson){
        builder.baseUrl(httpUrl)
                .client(client);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        if(options!=null){
            options.applyOptions(builder);
        }

        return builder.build();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder,@Nullable AppliesOptions.OkHttpClientOptions options){

        builder.addNetworkInterceptor(new LogInterceptor());
        if(options!=null) {
            options.applyOptions(builder);
        }

        return builder.build();
    }

    @Singleton
    @Provides
    Gson provideGson(GsonBuilder builder,@Nullable AppliesOptions.GsonOptions options){
        if(options!=null){
            options.applyOptions(builder);
        }
        return builder.create();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(Constants.DEFAULT_TIME_OUT,TimeUnit.SECONDS)
                .readTimeout(Constants.DEFAULT_TIME_OUT,TimeUnit.SECONDS);
    }

    @Singleton
    @Provides
    GsonBuilder provideGsonBuilder(){
        return new GsonBuilder();
    }


}
