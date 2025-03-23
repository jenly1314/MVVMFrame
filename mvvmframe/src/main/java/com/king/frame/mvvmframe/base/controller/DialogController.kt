package com.king.frame.mvvmframe.base.controller

import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.annotation.StyleRes
import com.king.frame.mvvmframe.R

/**
 * 对话框控制器；默认实现请查看[DefaultDialogController]
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface DialogController : Controller {

    /**
     * 显示进度对话框
     */
    fun showProgressDialog(
        layoutId: Int = R.layout.mvvmframe_progress_dialog,
        cancel: Boolean = false,
    )

    /**
     * 关闭进度对话框
     */
    fun dismissProgressDialog()

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
    fun showDialog(
        contentView: View,
        @StyleRes styleId: Int = R.style.mvvmframe_dialog,
        gravity: Int = Gravity.NO_GRAVITY,
        widthRatio: Float = 0.85f,
        x: Int = 0,
        y: Int = 0,
        horizontalMargin: Float = 0f,
        verticalMargin: Float = 0f,
        horizontalWeight: Float = 0f,
        verticalWeight: Float = 0f,
        backCancel: Boolean = true
    )

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
    fun setWindow(
        window: Window?,
        gravity: Int = Gravity.NO_GRAVITY,
        widthRatio: Float = 0.85f,
        x: Int = 0,
        y: Int = 0,
        horizontalMargin: Float = 0f,
        verticalMargin: Float = 0f,
        horizontalWeight: Float = 0f,
        verticalWeight: Float = 0f
    )

    /**
     * 关闭对话框
     */
    fun dismissDialog()

    /**
     * 关闭对话框
     */
    fun dismissDialog(dialog: Dialog?)

}
