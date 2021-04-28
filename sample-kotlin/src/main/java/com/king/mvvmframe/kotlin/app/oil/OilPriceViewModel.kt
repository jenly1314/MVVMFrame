package com.king.mvvmframe.kotlin.app.oil

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.frame.mvvmframe.base.BaseModel
import com.king.mvvmframe.kotlin.api.ApiService
import com.king.mvvmframe.kotlin.app.Constants
import com.king.mvvmframe.kotlin.app.base.BaseViewModel
import com.king.mvvmframe.kotlin.bean.OilPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.await
import javax.inject.Inject

/**
 * ViewModel 示例（Kotlin实现方式，与Java不同的是使用到了kotlin一些语法糖和协程相关，将公共部分提取到BaseViewModel，代码量大大减少）
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class OilPriceViewModel @Inject constructor(application: Application, model: BaseModel) : BaseViewModel(application, model) {


    val oilLiveData by lazy { MutableLiveData<List<OilPrice>>() }

    /**
     * 获取油价信息
     */
    fun getOilPriceInfo(){
        launch {
            val result = getRetrofitService(ApiService::class.java)
                    .getOilPriceInfo(Constants.OIL_PRICE_KEY)
                    .await()

            if(isSuccess(result)){
                result?.data?.let {
                    oilLiveData.value = it
                }
            }
        }
    }

}