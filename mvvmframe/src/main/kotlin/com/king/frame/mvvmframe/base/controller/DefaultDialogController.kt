package com.king.frame.mvvmframe.base.controller

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import android.view.Window
import androidx.annotation.StyleRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.king.frame.mvvmframe.base.BaseProgressDialog

/**
 * 对话框控制器
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class DefaultDialogController(private val context: Context) : DialogController {

    private var dialog: Dialog? = null

    private var progressDialog: Dialog? = null

    /**
     * 显示进度对话框
     */
    override fun showProgressDialog(
        layoutId: Int,
        cancel: Boolean,
    ) {
        dismissProgressDialog()
        progressDialog = BaseProgressDialog(context).also {
            it.setContentView(layoutId)
            it.setCanceledOnTouchOutside(cancel)
            it.show()
        }
    }

    /**
     * 关闭进度对话框
     */
    override fun dismissProgressDialog() {
        dismissDialog(progressDialog)
    }

    /**
     * 显示对话框
     * @param contentView 弹框内容视图
     * @param styleId Dialog样式
     * @param gravity Dialog的对齐方式
     * @param widthRatio 宽度比例，根据屏幕宽度计算得来
     * @param x x轴偏移量，需与 gravity 结合使用
     * @param y y轴偏移量，需与 gravity 结合使用
     * @param horizontalMargin 水平方向边距
     * @param verticalMargin 垂直方向边距
     * @param horizontalWeight 水平方向权重
     * @param verticalWeight 垂直方向权重
     * @param backCancel 返回键是否可取消（默认为true，false则拦截back键）
     */
    override fun showDialog(
        contentView: View,
        @StyleRes styleId: Int,
        gravity: Int,
        widthRatio: Float,
        x: Int,
        y: Int,
        horizontalMargin: Float,
        verticalMargin: Float,
        horizontalWeight: Float,
        verticalWeight: Float,
        backCancel: Boolean,
    ) {
        dismissDialog()
        dialog = Dialog(context, styleId).also {
            it.setContentView(contentView)
            it.setCanceledOnTouchOutside(false)
            it.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (backCancel) {
                        dismissDialog()
                    }
                    return@OnKeyListener true
                }
                false
            })
            setWindow(
                window = it.window,
                gravity = gravity,
                widthRatio = widthRatio,
                x = x,
                y = y,
                horizontalMargin = horizontalMargin,
                verticalMargin = verticalMargin,
                horizontalWeight = horizontalWeight,
                verticalWeight = verticalWeight
            )
            it.show()
        }

    }

    /**
     * 设置 Window 布局相关参数
     * @param window [Window]
     * @param gravity Dialog的对齐方式
     * @param widthRatio 宽度比例，根据屏幕宽度计算得来
     * @param x x轴偏移量，需与 gravity 结合使用
     * @param y y轴偏移量，需与 gravity 结合使用
     * @param horizontalMargin 水平方向边距
     * @param verticalMargin 垂直方向边距
     * @param horizontalWeight 水平方向权重
     * @param verticalWeight 垂直方向权重
     */
    override fun setWindow(
        window: Window?,
        gravity: Int,
        widthRatio: Float,
        x: Int,
        y: Int,
        horizontalMargin: Float,
        verticalMargin: Float,
        horizontalWeight: Float,
        verticalWeight: Float,
    ) {
        window?.attributes?.let {
            it.width = (context.resources.displayMetrics.widthPixels * widthRatio).toInt()
            it.gravity = gravity
            it.x = x
            it.y = y
            it.horizontalMargin = horizontalMargin
            it.verticalMargin = verticalMargin
            it.horizontalWeight = horizontalWeight
            it.verticalWeight = verticalWeight
            window.attributes = it
        }
    }

    override fun dismissDialog() {
        dismissDialog(dialog)
    }

    /**
     * 关闭对话框
     */
    override fun dismissDialog(dialog: Dialog?) {
        dialog?.dismiss()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        super.onStateChanged(source, event)
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                dismissProgressDialog()
                dismissDialog()
            }

            else -> Unit
        }
    }
}
