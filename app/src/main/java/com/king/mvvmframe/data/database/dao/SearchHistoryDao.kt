package com.king.mvvmframe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.king.mvvmframe.data.model.SearchHistory
import kotlinx.coroutines.flow.Flow

/**
 * 搜索历史
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Dao
interface SearchHistoryDao {
    /**
     * 插入一条历史数据
     * @param searchHistory
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistory: SearchHistory)

    /**
     * 删除所有历史数据
     */
    @Query("DELETE FROM SearchHistory")
    suspend fun deleteAll()

    /**
     * 获取历史数据对应的[Flow]
     *
     * @param size 获取历史记录的条数
     */
    @Query("SELECT * FROM SearchHistory ORDER BY timestamp DESC LIMIT :size")
    fun getHistoryFlow(size: Int): Flow<List<SearchHistory>?>
}
