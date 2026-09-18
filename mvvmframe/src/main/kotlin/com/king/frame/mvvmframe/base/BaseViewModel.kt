package com.king.frame.mvvmframe.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.king.frame.mvvmframe.base.controller.ToastController
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.reflect.ParameterizedType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * 如果您继承使用了 BaseViewModel 或其子类，你需要参照如下方式添加 @HiltViewModel 和 @Inject 注解标记，来进行注入
 *
 * ```
 * // 示例
 * @HiltViewModel
 * class YourViewModel @Inject constructor(): BaseViewModel() {
 *
 * }
 * ```
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    /**
     * 加载状态
     */
    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    /**
     * 加载状态
     */
    val loadingState: Flow<Boolean> = _loadingState.asStateFlow()

    /**
     * 消息事件
     */
    private val _messageEvent: MutableSharedFlow<String> = MutableSharedFlow()

    /**
     * 消息事件
     */
    val messageEvent: Flow<String> = _messageEvent.asSharedFlow()

    /**
     * 显示加载状态
     */
    fun showLoading() {
        _loadingState.tryEmit(true)
    }

    /**
     * 隐藏加载状态
     */
    fun hideLoading() {
        _loadingState.tryEmit(false)
    }

    /**
     * 发送消息；如果消息内容不为空，则将消息发送到页面，通过[ToastController.showToast]进行显示
     * @param message 消息内容
     */
    fun sendMessage(message: String?) {
        message?.also {
            viewModelScope.launch {
                _messageEvent.emit(it)
            }
        }
    }

    companion object {

        /**
         * 获取泛型VM对应的类
         */
        @Suppress("UNCHECKED_CAST")
        internal fun <VM> Any.getVMClass(): Class<VM> {
            var cls: Class<*>? = this.javaClass
            var vmClass: Class<VM>? = null
            while (vmClass == null && cls != null) {
                vmClass = getVMClass(cls)
                cls = cls.superclass
            }
            if (vmClass == null) {
                vmClass = BaseViewModel::class.java as Class<VM>
            }
            return vmClass
        }

        /**
         * 根据传入的 cls 获取泛型VM对应的类
         */
        @Suppress("UNCHECKED_CAST")
        private fun <VM> getVMClass(cls: Class<*>): Class<VM>? {
            val type = cls.genericSuperclass
            if (type is ParameterizedType) {
                val types = type.actualTypeArguments
                for (t in types) {
                    if (t is Class<*>) {
                        if (BaseViewModel::class.java.isAssignableFrom(t)) {
                            return t as? Class<VM>
                        }
                    } else if (t is ParameterizedType) {
                        val rawType = t.rawType
                        if (rawType is Class<*>) {
                            if (BaseViewModel::class.java.isAssignableFrom(rawType)) {
                                return rawType as? Class<VM>
                            }
                        }
                    }
                }
            }
            return null
        }
    }
}
