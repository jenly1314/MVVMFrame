package com.king.mvvmframe.app.city

import android.app.Application
import com.king.mvvmframe.app.base.BaseViewModel
import com.king.mvvmframe.data.model.City
import com.king.mvvmframe.data.repository.CityRepository
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
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    application: Application
) : BaseViewModel(application) {

    private val _cityFlow = MutableStateFlow<List<City>>(emptyList())
    val cityFlow = _cityFlow.asSharedFlow()

    /**
     * 获取热门城市
     */
    fun getHotCities() {
        launch {
            cityRepository.getHotCities().also {
                _cityFlow.emit(it)
            }
        }
    }

}
