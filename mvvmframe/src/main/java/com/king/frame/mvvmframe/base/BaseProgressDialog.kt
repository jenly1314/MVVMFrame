package com.king.frame.mvvmframe.base

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import com.king.frame.mvvmframe.R

/**
 * 对话框基类
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
open class BaseProgressDialog : Dialog {
    constructor(context: Context) : this(context, R.style.mvvmframe_progress_dialog)
    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        window?.attributes?.gravity = Gravity.CENTER
    }
}
