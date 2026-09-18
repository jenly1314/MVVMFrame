package com.king.mvvmframe.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 搜索历史
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Entity(indices = [Index(value = ["word"], unique = true)])
data class SearchHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String,
    val timestamp: Long,
)
