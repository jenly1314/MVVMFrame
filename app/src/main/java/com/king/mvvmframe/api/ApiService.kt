package com.king.mvvmframe.api

import com.king.mvvmframe.app.Constants
import com.king.mvvmframe.bean.City
import com.king.mvvmframe.bean.DictionaryInfo
import com.king.mvvmframe.bean.OilPrice
import com.king.mvvmframe.bean.Result
import com.king.retrofit.retrofithelper.annotation.DomainName
import com.king.retrofit.retrofithelper.annotation.Timeout
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface ApiService {
    /**
     * 查询国内油价
     */
    @GET("gnyj/query")
    suspend fun getOilPriceInfo(@Query("key") key: String = Constants.OIL_PRICE_KEY): Result<List<OilPrice>>

    /**
     * 获取字典信息- 新华字典
     * @param key
     * @return
     */
    @DomainName(Constants.DOMAIN_DICTIONARY)
    @GET("xhzd/query")
    suspend fun getDictionaryInfo(
        @Query("word") word: String,
        @Query("key") key: String = Constants.DICTIONARY_KEY
    ): Result<DictionaryInfo>

    /**
     * 动态改变 BaseUrl 示例
     * @return
     */
    @DomainName(Constants.DOMAIN_JENLY)
    @Timeout(connectTimeout = 10, readTimeout = 15, writeTimeout = 10)
    @GET("api/city/hotCities.json")
    suspend fun getHotCities(): List<City>
}