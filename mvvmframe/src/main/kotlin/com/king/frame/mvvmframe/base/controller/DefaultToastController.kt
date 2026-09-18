package com.king.frame.mvvmframe.base.controller

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.hjq.toast.Toaster
import com.king.frame.mvvmframe.base.stringResIdToString

/**
 * 默认的Toast控制器
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class DefaultToastController(private val context: Context) : ToastController {

    private var toast: Toast? = null
    private var isDestroy = false

    override fun showToast(text: CharSequence) {
        if (Toaster.isInit()) {
            Toaster.show(text)
        } else {
            show(text)
        }
    }

    override fun showToast(resId: Int) {
        showToast(context.applicationContext.stringResIdToString(resId))
    }

    private fun show(text: CharSequence) {
        if (isDestroy) {
            return
        }
        toast?.cancel()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        super.onStateChanged(source, event)
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                isDestroy = true
                toast?.cancel()
                toast = null
            }

            else -> Unit
        }
    }
}
