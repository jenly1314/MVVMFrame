package com.king.mvvmframe.app.base

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.king.base.util.SystemUtils
import com.king.frame.mvvmframe.base.BaseAndroidViewModel
import com.king.mvvmframe.bean.Result
import com.king.mvvmframe.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * 基类
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltViewModel
open class BaseViewModel @Inject constructor(application: Application) : BaseAndroidViewModel(application) {

    fun isSuccess(result: Result<*>, showError: Boolean = true): Boolean {
        if (result.isSuccess()) {
            return true
        }
        if (showError) {
            sendMessage(result.message)
        }
        return false
    }

    fun launch(showLoading: Boolean = true, block: suspend () -> Unit) =
        launch(showLoading, block) {
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
        }

    fun launch(
        showLoading: Boolean,
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit
    ) = viewModelScope.launch {
        try {
            if (showLoading) {
                showLoading()
            }
            block()
        } catch (e: Throwable) {
            error(e)
        }
        if (showLoading) {
            hideLoading()
        }
    }
}