package com.king.mvvmframe.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.king.mvvmframe.bean.SearchHistory
import com.king.mvvmframe.data.dao.SearchHistoryDao

/**
 * 数据库
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * SearchHistoryDao
     */
    abstract val searchHistoryDao: SearchHistoryDao
}