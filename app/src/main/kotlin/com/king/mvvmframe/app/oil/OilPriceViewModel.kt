package com.king.mvvmframe.app.oil

import android.app.Application
import com.king.mvvmframe.app.base.BaseViewModel
import com.king.mvvmframe.data.model.OilPrice
import com.king.mvvmframe.data.repository.OilPriceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * 示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltViewModel
class OilPriceViewModel @Inject constructor(
    private val oilPriceRepository: OilPriceRepository,
    application: Application
) : BaseViewModel(application) {


    private val _oilPriceFlow = MutableStateFlow<List<OilPrice>>(emptyList())
    val oilPriceFlow = _oilPriceFlow.asSharedFlow()

    /**
     * 获取油价信息
     */
    fun getOilPriceInfo() {
        launch {
            val result = oilPriceRepository.getOilPriceInfo()
            if (isSuccess(result)) {
                result.data?.also {
                    _oilPriceFlow.emit(it)
                }
            }
        }
    }

}
