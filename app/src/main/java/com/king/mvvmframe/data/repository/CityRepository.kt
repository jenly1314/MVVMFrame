package com.king.mvvmframe.data.repository

import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.mvvmframe.data.model.City
import com.king.mvvmframe.data.service.CityService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 城市数据仓库
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Singleton
class CityRepository @Inject constructor(private val dataSource: DataSource) {

    private val cityService: CityService by lazy {
        dataSource.getRetrofitService(CityService::class.java)
    }

    /**
     * 获取热门城市列表
     */
    suspend fun getHotCities(): List<City> {
        return cityService.getHotCities()
    }
}
