package com.king.frame.mvvmframe.base

import android.content.Context
import android.content.res.Resources

/**
 * 扩展函数
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */

/**
 * 通过id获取对应的String资源
 */
internal fun Context.stringResIdToString(id: Int): String {
    return resources.stringResIdToString(id)
}

/**
 * 通过id获取对应的String资源
 */
internal fun Resources.stringResIdToString(id: Int): String {
    return try {
        this.getString(id)
    } catch (e: Resources.NotFoundException) {
        // 当找不到ID对应的资源时，则直接返回ID本身
        return id.toString()
    }
}