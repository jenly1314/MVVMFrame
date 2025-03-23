package com.king.mvvmframe.app.base

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.king.base.util.SystemUtils
import com.king.frame.mvvmframe.base.BaseAndroidViewModel
import com.king.mvvmframe.data.model.Result
import com.king.mvvmframe.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 基类
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltViewModel
open class BaseViewModel @Inject constructor(application: Application) : BaseAndroidViewModel(application) {

    /**
     * 是否成功
     */
    fun isSuccess(result: Result<*>, showError: Boolean = true): Boolean {
        if (result.isSuccess()) {
            return true
        }
        if (showError) {
            sendMessage(result.message)
        }
        return false
    }

    /**
     * 启动一个协程
     */
    fun launch(
        showLoading: Boolean = true,
        context: CoroutineContext = EmptyCoroutineContext,
        error: suspend (Throwable) -> Unit = {
            Timber.w(it)
            if (SystemUtils.isNetWorkActive(getApplication())) {
                when (it) {
                    is SocketTimeoutException -> sendMessage(R.string.result_connect_timeout_error)
                    is ConnectException -> sendMessage(R.string.result_connect_failed_error)
                    else -> sendMessage(R.string.result_error)
                }
            } else {
                sendMessage(R.string.result_network_unavailable_error)
            }
        },
        block: suspend () -> Unit,
    ) = viewModelScope.launch(context = context) {
        try {
            if (showLoading) {
                showLoading()
            }
            block()
        } catch (e: Throwable) {
            error(e)
        } finally {
            if (showLoading) {
                hideLoading()
            }
        }
    }
}
