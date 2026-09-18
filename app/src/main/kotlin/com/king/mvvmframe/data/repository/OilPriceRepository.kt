package com.king.mvvmframe.data.repository

import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.mvvmframe.constant.Constants
import com.king.mvvmframe.data.model.OilPrice
import com.king.mvvmframe.data.model.Result
import com.king.mvvmframe.data.service.OilPriceService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 油价价格数据仓库
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Singleton
class OilPriceRepository @Inject constructor(private val dataSource: DataSource) {

    private val oilPriceService: OilPriceService by lazy {
        dataSource.getRetrofitService(OilPriceService::class.java)
    }

    /**
     * 获取查询国内油价
     */
    suspend fun getOilPriceInfo(key: String = Constants.OIL_PRICE_KEY): Result<List<OilPrice>> {
        return oilPriceService.getOilPriceInfo(key)
    }
}
