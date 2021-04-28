package com.king.mvvmframe.api;

import com.king.mvvmframe.app.Constants;
import com.king.mvvmframe.bean.DictionaryInfo;
import com.king.mvvmframe.bean.OilPrice;
import com.king.mvvmframe.bean.Result;
import com.king.retrofit.retrofithelper.annotation.DomainName;
import com.king.retrofit.retrofithelper.annotation.Timeout;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface ApiService {

    /**
     * 查询国内油价
     * @return
     */
    @GET("gnyj/query")
    Call<Result<List<OilPrice>>> getOilPriceInfo(@Query("key")String key);


    /**
     * 获取字典信息- 新华字典
     * @param key
     * @return
     */
    @DomainName(Constants.DOMAIN_DICTIONARY)
    @GET("xhzd/query")
    Call<Result<DictionaryInfo>> getDictionaryInfo(@Query("key")String key, @Query("word")String word);

    /**
     * 动态改变 BaseUrl 示例
     * @return
     */
    @DomainName(Constants.DOMAIN_JENLY)
    @Timeout(connectTimeout = 14,readTimeout = 15,writeTimeout = 14)
    @GET("api/city/hotCities.json")
    Call<List<Map<String,Object>>> getHotCities();

}
