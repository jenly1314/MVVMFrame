package com.king.mvvmframe.api;

import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface ApiService {


    @GET("recommendPoetry")
    Call<Result<PoetryInfo>> getRecommendPoetry();
}
