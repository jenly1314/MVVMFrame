package com.king.frame.mvvmframe.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.Window
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.hjq.toast.Toaster
import com.king.frame.mvvmframe.R
import com.king.frame.mvvmframe.util.JumpDebounce
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

/**
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * 如果您继承使用了 BaseActivity 或其子类，你需要参照如下方式添加 @AndroidEntryPoint 注解标记，来进行注入
 *
 *
 * ```
 * // 示例
 * @AndroidEntryPoint
 * class YourActivity: BaseActivity() {
 *
 * }
 * ```
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
abstract class BaseActivity<VM : BaseViewModel, VDB : ViewDataBinding> : AppCompatActivity(),
    IView<VM> {

    lateinit var viewModel: VM
        private set

    var viewDataBinding: VDB? = null
        private set
    val binding: VDB
        get() = viewDataBinding!!

    var dialog: Dialog? = null
        private set
    var progressDialog: Dialog? = null
        private set

    private val jumpDebounce by lazy {
        JumpDebounce()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initViewModel()
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding?.unbind()

    }

    /**
     * 初始化ContentView；
     */
    open fun initContentView() {
        if (isBinding()) {
            viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        } else {
            setContentView(getLayoutId())
        }
    }

    /**
     * 初始化[viewModel]
     */
    private fun initViewModel() {
        viewModel = createViewModel()
        lifecycleScope.launch {
            viewModel.loadingState.flowWithLifecycle(lifecycle).collect {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.messageEvent.flowWithLifecycle(lifecycle).collect {
                showToast(it)
            }
        }
    }

    /**
     * 获取泛型VM对应的类
     */
    @Suppress("UNCHECKED_CAST")
    private fun getVMClass(): Class<VM> {
        var cls: Class<*>? = javaClass
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
    private fun getVMClass(cls: Class<*>): Class<VM>? {
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

    /**
     * 是否使用 ViewDataBinding；默认为：true
     */
    override fun isBinding(): Boolean {
        return true
    }

    override fun createViewModel(): VM {
        return obtainViewModel(getVMClass())
    }

    fun <T : ViewModel> obtainViewModel(vmClass: Class<T>): T {
        return ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory,
            defaultViewModelCreationExtras
        )[vmClass]
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        dismissProgressDialog()
    }

    override fun showToast(text: CharSequence) {
        Toaster.show(text)
    }

    override fun showToast(@StringRes resId: Int) {
        showToast(stringResIdToString(resId))
    }

    /**
     * 显示进度对话框
     */
    fun showProgressDialog(
        layoutId: Int = R.layout.mvvmframe_progress_dialog,
        cancel: Boolean = false
    ) {
        dismissProgressDialog()
        progressDialog = BaseProgressDialog(this).also {
            it.setContentView(layoutId)
            it.setCanceledOnTouchOutside(cancel)
            it.show()
        }
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
    ) {
        dismissDialog()
        dialog = Dialog(this, styleId).also {
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
                it.window,
                gravity,
                widthRatio,
                x,
                y,
                horizontalMargin,
                verticalMargin,
                horizontalWeight,
                verticalWeight
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
    open fun setWindow(
        window: Window?,
        gravity: Int = Gravity.NO_GRAVITY,
        widthRatio: Float = 0.85f,
        x: Int = 0,
        y: Int = 0,
        horizontalMargin: Float = 0f,
        verticalMargin: Float = 0f,
        horizontalWeight: Float = 0f,
        verticalWeight: Float = 0f
    ) {
        window?.attributes?.let {
            it.width = (resources.displayMetrics.widthPixels * widthRatio).toInt()
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

    /**
     * 关闭进度对话框
     */
    fun dismissProgressDialog() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

    /**
     * 关闭对话框
     */
    fun dismissDialog(dialog: Dialog? = this.dialog) {
        dialog?.dismiss()
    }

    @Suppress("DEPRECATION")
    @Deprecated("DeprecatedIsStillUsed")
    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        if (jumpDebounce.isIgnoreJump(intent)) {
            return
        }
        super.startActivityForResult(intent, requestCode, options)
    }

    /**
     * 获取[Context]
     */
    fun getContext() = this
}