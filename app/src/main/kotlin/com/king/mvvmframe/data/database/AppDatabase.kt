package com.king.mvvmframe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.king.mvvmframe.data.model.SearchHistory
import com.king.mvvmframe.data.database.dao.SearchHistoryDao

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
