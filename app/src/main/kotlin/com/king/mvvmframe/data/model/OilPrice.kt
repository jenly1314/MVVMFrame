package com.king.mvvmframe.data.model

import com.google.gson.annotations.SerializedName

/**
 * 石油价格
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
data class OilPrice(
    var city: String,
    @SerializedName("92h")
    val oil92: String,
    @SerializedName("95h")
    val oil95: String,
    @SerializedName("98h")
    val oil98: String,
    @SerializedName("0h")
    val oil0: String,
) {
    fun getOil92Str() = "¥ $oil92"

    fun getOil95Str() = "¥ $oil95"

    fun getOil98Str() = "¥ $oil98"

    fun getOil0Str() = "¥ $oil0"
}
