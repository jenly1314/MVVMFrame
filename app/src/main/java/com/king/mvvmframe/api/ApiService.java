package com.king.mvvmframe.api;

import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface ApiService {

    /**
     * 获取随机十条推荐的诗词
     * @return
     */
    @POST("poetry/poetrys/randomTenPoetry")
    Call<Result<List<PoetryInfo>>> getRecommendPoetry();

    /**
     * 模糊搜索诗词
     * @param keyword 搜索诗词名、诗词内容、诗词作者
     * @param page 页码
     * @return
     */
    @POST("poetry/poetrys/searchPoetry")
    Call<Result<List<PoetryInfo>>> searchPoetry(@Query("keyword")String keyword,@Query("page")int page);
}
