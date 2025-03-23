package com.king.mvvmframe.data.model

import com.google.gson.annotations.SerializedName

/**
 * 结果
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
data class Result<T>(
    @SerializedName("error_code")
    val code: Int,
    @SerializedName("reason")
    var message: String? = null,
    @SerializedName("result")
    var data: T? = null
) {
    /**
     * 是否成功
     */
    fun isSuccess() = code == 0
}
