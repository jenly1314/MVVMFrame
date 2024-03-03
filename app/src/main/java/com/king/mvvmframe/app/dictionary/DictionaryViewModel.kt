package com.king.mvvmframe.app.dictionary

import android.app.Application
import com.king.frame.mvvmframe.data.Repository
import com.king.mvvmframe.R
import com.king.mvvmframe.api.ApiService
import com.king.mvvmframe.app.base.BaseViewModel
import com.king.mvvmframe.bean.DictionaryInfo
import com.king.mvvmframe.bean.SearchHistory
import com.king.mvvmframe.data.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

/**
 * 示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : BaseViewModel(application) {

    private val apiService: ApiService by lazy {
        repository.getRetrofitService(ApiService::class.java)
    }

    private val searchHistoryDao by lazy {
        repository.getRoomDatabase(AppDatabase::class.java, null).searchHistoryDao
    }

    private val _dictionaryFlow = MutableStateFlow<DictionaryInfo?>(null)
    val dictionaryFlow = _dictionaryFlow.asSharedFlow()

    /**
     * 获取字典信息
     */
    fun getDictionaryInfo(word: String) {
        launch {
            searchHistoryDao.insert(
                SearchHistory(
                    word = word,
                    timestamp = System.currentTimeMillis()
                )
            )

            val result = apiService.getDictionaryInfo(word)
            if (isSuccess(result)) {
                _dictionaryFlow.emit(result.data)
            }
        }
    }

    /**
     * 获取搜索历史
     */
    fun getSearchHistoryFlow() = searchHistoryDao.getHistoryFlow(10).filterNotNull()

    /**
     * 删除搜索历史
     */
    fun deleteSearchHistory() {
        launch {
            searchHistoryDao.deleteAll()
            sendMessage(R.string.cleared)
        }
    }

}