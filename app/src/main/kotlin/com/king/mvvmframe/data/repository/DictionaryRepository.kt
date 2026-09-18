package com.king.mvvmframe.data.repository

import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.mvvmframe.constant.Constants
import com.king.mvvmframe.data.database.AppDatabase
import com.king.mvvmframe.data.model.DictionaryInfo
import com.king.mvvmframe.data.model.Result
import com.king.mvvmframe.data.model.SearchHistory
import com.king.mvvmframe.data.service.DictionaryService
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

/**
 * 城市数据仓库
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Singleton
class DictionaryRepository @Inject constructor(private val dataSource: DataSource) {

    private val dictionaryService: DictionaryService by lazy {
        dataSource.getRetrofitService(DictionaryService::class.java)
    }

    private val searchHistoryDao by lazy {
        dataSource.getRoomDatabase(AppDatabase::class.java).searchHistoryDao
    }

    /**
     * 获取热门城市列表
     */
    suspend fun getDictionaryInfo(
        word: String,
        key: String = Constants.DICTIONARY_KEY
    ): Result<DictionaryInfo> {
        searchHistoryDao.insert(
            SearchHistory(
                word = word,
                timestamp = System.currentTimeMillis()
            )
        )
        return dictionaryService.getDictionaryInfo(word, key)
    }

    /**
     * 获取搜索历史
     */
    fun getSearchHistoryFlow(size: Int = 10): Flow<List<SearchHistory>> {
        return searchHistoryDao.getHistoryFlow(size).filterNotNull()
    }

    /**
     * 清空搜索历史
     */
    suspend fun clearSearchHistory() {
        searchHistoryDao.deleteAll()
    }
}
