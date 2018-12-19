package com.king.mvvmframe.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.king.mvvmframe.bean.SearchHistory;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Dao
public interface SearchHistoryDao {

    /**
     * 插入一条历史（去重）
     * @param searchHistory
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchHistory searchHistory);

    /**
     * 删除历史
     * @param searchHistory
     */
    @Delete
    void delete(SearchHistory searchHistory);

    /**
     * 清空历史
     */
    @Query("DELETE FROM SearchHistory")
    void deleteAll();

    /**
     * 获取所有历史
     * @return
     */
    @Query("SELECT * FROM SearchHistory")
    LiveData<List<SearchHistory>> getAllHistory();

    /**
     * 获取历史
     * @param count 获取历史记录的条数
     * @return
     */
    @Query("SELECT * FROM SearchHistory ORDER BY time DESC LIMIT :count")
    LiveData<List<SearchHistory>> getHistory(int count);

}
