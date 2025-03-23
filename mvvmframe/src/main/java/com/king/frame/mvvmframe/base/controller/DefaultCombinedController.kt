package com.king.frame.mvvmframe.base.controller

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * 组合控制器
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class DefaultCombinedController(
    private val context: Context,
    private val dialogController: DialogController = DefaultDialogController(context),
    private val loadingController: LoadingController = DefaultLoadingController(context),
    private val toastController: ToastController = DefaultToastController(context),
) : CombinedController, DialogController by dialogController,
    LoadingController by loadingController,
    ToastController by toastController {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        dialogController.onStateChanged(source, event)
        loadingController.onStateChanged(source, event)
        toastController.onStateChanged(source, event)
    }
}
