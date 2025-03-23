package com.king.mvvmframe.data.service

import com.king.mvvmframe.constant.Constants
import com.king.mvvmframe.data.model.City
import com.king.retrofit.retrofithelper.annotation.DomainName
import com.king.retrofit.retrofithelper.annotation.Timeout
import retrofit2.http.GET

/**
 * 城市API
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface CityService {

    /**
     * 动态改变 BaseUrl 示例
     * @return
     */
    @DomainName(Constants.DOMAIN_JENLY)
    @Timeout(connectTimeout = 10, readTimeout = 15, writeTimeout = 10)
    @GET("api/city/hotCities.json")
    suspend fun getHotCities(): List<City>
}
