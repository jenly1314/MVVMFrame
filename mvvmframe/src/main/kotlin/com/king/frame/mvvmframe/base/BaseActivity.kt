package com.king.frame.mvvmframe.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.king.frame.mvvmframe.base.BaseViewModel.Companion.getVMClass
import com.king.frame.mvvmframe.base.controller.CombinedController
import com.king.frame.mvvmframe.base.controller.DefaultCombinedController
import com.king.frame.mvvmframe.util.JumpDebounce
import kotlinx.coroutines.launch

/**
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * 如果您继承使用了 BaseActivity 或其子类，你需要参照如下方式添加 @AndroidEntryPoint 注解标记，来进行注入
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
    IView<VM>, CombinedController {

    protected open val combinedController: CombinedController by lazy {
        DefaultCombinedController(this)
    }

    private val jumpDebounce by lazy {
        JumpDebounce()
    }

    lateinit var viewModel: VM
        private set

    var viewDataBinding: VDB? = null
        private set

    val binding: VDB
        get() = viewDataBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initViewModel()
        initData(savedInstanceState)
        lifecycle.addObserver(combinedController)
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
     * 是否使用 ViewDataBinding；默认为：true
     */
    override fun isBinding(): Boolean {
        return true
    }

    /**
     * 创建ViewModel
     */
    override fun createViewModel(): VM {
        return obtainViewModel(getVMClass())
    }

    /**
     * 获取ViewModel
     */
    fun <T : ViewModel> obtainViewModel(vmClass: Class<T>): T {
        return ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory,
            defaultViewModelCreationExtras
        )[vmClass]
    }

    override fun showLoading() {
        combinedController.showLoading()
    }

    override fun hideLoading() {
        combinedController.hideLoading()
    }

    override fun showProgressDialog(layoutId: Int, cancel: Boolean) {
        combinedController.showProgressDialog(layoutId, cancel)
    }

    override fun dismissProgressDialog() {
        combinedController.dismissProgressDialog()
    }

    override fun showDialog(
        contentView: View,
        styleId: Int,
        gravity: Int,
        widthRatio: Float,
        x: Int,
        y: Int,
        horizontalMargin: Float,
        verticalMargin: Float,
        horizontalWeight: Float,
        verticalWeight: Float,
        backCancel: Boolean
    ) {
        combinedController.showDialog(
            contentView = contentView,
            styleId = styleId,
            gravity = gravity,
            widthRatio = widthRatio,
            x = x,
            y = y,
            horizontalMargin = horizontalMargin,
            verticalMargin = verticalMargin,
            horizontalWeight = horizontalWeight,
            verticalWeight = verticalWeight,
            backCancel = backCancel,
        )
    }

    override fun setWindow(
        window: Window?,
        gravity: Int,
        widthRatio: Float,
        x: Int,
        y: Int,
        horizontalMargin: Float,
        verticalMargin: Float,
        horizontalWeight: Float,
        verticalWeight: Float
    ) {
        combinedController.setWindow(
            window = window,
            gravity = gravity,
            widthRatio = widthRatio,
            x = x,
            y = y,
            horizontalMargin = horizontalMargin,
            verticalMargin = verticalMargin,
            horizontalWeight = horizontalWeight,
            verticalWeight = verticalWeight,
        )
    }

    override fun dismissDialog() {
        combinedController.dismissDialog()
    }

    override fun dismissDialog(dialog: Dialog?) {
        combinedController.dismissDialog(dialog)
    }

    override fun showToast(text: CharSequence) {
        combinedController.showToast(text)
    }

    override fun showToast(resId: Int) {
        combinedController.showToast(resId)
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
