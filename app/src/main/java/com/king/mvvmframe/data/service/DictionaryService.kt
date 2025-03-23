package com.king.mvvmframe.data.service

import com.king.mvvmframe.constant.Constants
import com.king.mvvmframe.data.model.DictionaryInfo
import com.king.mvvmframe.data.model.Result
import com.king.retrofit.retrofithelper.annotation.DomainName
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 字典API
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface DictionaryService {

    /**
     * 获取字典信息- 新华字典
     * @param key
     * @return
     */
    @DomainName(Constants.DOMAIN_DICTIONARY)
    @GET("xhzd/query")
    suspend fun getDictionaryInfo(
        @Query("word") word: String,
        @Query("key") key: String,
    ): Result<DictionaryInfo>

}
