package com.king.mvvmframe.kotlin.api

import com.king.mvvmframe.kotlin.bean.Result
import com.king.mvvmframe.kotlin.bean.OilPrice
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
interface ApiService {

    /**
     * 查询国内油价
     * @return
     */
    @GET("gnyj/query")
    fun getOilPriceInfo(@Query("key") key: String): Call<Result<List<OilPrice>>>
}