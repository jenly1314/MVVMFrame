package com.king.frame.mvvmframe.base

import android.app.Application
import android.content.res.Resources.NotFoundException
import androidx.annotation.StringRes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * 如果您继承使用了 BaseAndroidViewModel 或其子类，你需要参照如下方式添加 @HiltViewModel 和 @Inject 注解标记，来进行注入
 *
 *
 * ```
 * // 示例
 * @HiltViewModel
 * class YourViewModel @Inject constructor(): BaseAndroidViewModel() {
 *
 * }
 * ```
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltViewModel
open class BaseAndroidViewModel @Inject constructor(private val application: Application) :
    BaseViewModel() {

    /**
     * 获取[Application]
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Application> getApplication(): T {
        return application as T
    }

    /**
     * 发送消息；如果消息内容不为空，则将消息发送到页面，通过[IView.showToast]进行显示
     * @param resId 消息资源ID
     */
    fun sendMessage(@StringRes resId: Int) {
        sendMessage(application.stringResIdToString(resId))
    }
}