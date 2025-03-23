package com.king.frame.mvvmframe.base.controller

import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Toast控制器；默认实现请查看[DefaultToastController]
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface ToastController : Controller {

    /**
     * 通过[Toast]显示提示信息
     */
    fun showToast(text: CharSequence)

    /**
     * 通过[Toast]显示提示信息
     */
    fun showToast(@StringRes resId: Int)

}
