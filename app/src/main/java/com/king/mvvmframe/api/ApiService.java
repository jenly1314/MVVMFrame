package com.king.mvvmframe.api;

import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface ApiService {

    /**
     * 推荐诗词
     * @return
     */
    @GET("recommendPoetry")
    Call<Result<PoetryInfo>> getRecommendPoetry();

    /**
     * 模糊搜索诗词
     * @param name 搜索诗词名、诗词内容、诗词作者
     * @return
     */
    @GET("likePoetry")
    Call<Result<List<PoetryInfo>>> getLikePoetry(@Query("name")String name);
}
