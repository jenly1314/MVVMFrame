package com.king.mvvmframe.data.service

import com.king.mvvmframe.data.model.OilPrice
import com.king.mvvmframe.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 石油价格API
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface OilPriceService {

    /**
     * 查询国内油价
     */
    @GET("gnyj/query")
    suspend fun getOilPriceInfo(@Query("key") key: String): Result<List<OilPrice>>

}
