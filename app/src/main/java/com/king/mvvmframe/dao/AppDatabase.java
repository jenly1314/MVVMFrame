package com.king.mvvmframe.dao;


import com.king.mvvmframe.bean.SearchHistory;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Database(entities = {SearchHistory.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

//    public abstract SearchHistoryDao searchHistoryDao();
}
