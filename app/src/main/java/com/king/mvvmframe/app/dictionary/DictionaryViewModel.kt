package com.king.mvvmframe.app.dictionary

import android.app.Application
import com.king.mvvmframe.R
import com.king.mvvmframe.app.base.BaseViewModel
import com.king.mvvmframe.data.model.DictionaryInfo
import com.king.mvvmframe.data.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * 示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
    application: Application
) : BaseViewModel(application) {

    private val _dictionaryFlow = MutableStateFlow<DictionaryInfo?>(null)
    val dictionaryFlow = _dictionaryFlow.asSharedFlow()

    /**
     * 获取字典信息
     */
    fun getDictionaryInfo(word: String) {
        launch {
            val result = dictionaryRepository.getDictionaryInfo(word)
            if (isSuccess(result)) {
                _dictionaryFlow.emit(result.data)
            }
        }
    }

    /**
     * 获取搜索历史
     */
    fun getSearchHistoryFlow() = dictionaryRepository.getSearchHistoryFlow()

    /**
     * 删除搜索历史
     */
    fun clearSearchHistory() {
        launch {
            dictionaryRepository.clearSearchHistory()
            sendMessage(R.string.cleared)
        }
    }

}
