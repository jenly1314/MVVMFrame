package com.king.frame.mvvmframe.base.controller

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.king.frame.mvvmframe.R
import com.king.frame.mvvmframe.base.BaseProgressDialog

/**
 * 加载控制器
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class DefaultLoadingController(private val context: Context) : LoadingController {

    private var loadingDialog: Dialog? = null

    override fun showLoading() {
        hideLoading()
        loadingDialog = BaseProgressDialog(context).also {
            it.setContentView(R.layout.mvvmframe_loading)
            it.setCanceledOnTouchOutside(false)
            it.show()
        }
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        super.onStateChanged(source, event)
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                hideLoading()
            }

            else -> Unit
        }
    }
}
