package com.king.frame.mvvmframe.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
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
 * 如果您继承使用了 BaseDialogFragment 或其子类，你需要参照如下方式添加 @AndroidEntryPoint 注解标记，来进行注入
 *
 *
 * ```
 * // 示例
 * @AndroidEntryPoint
 * class YourFragment: BaseDialogFragment() {
 *
 * }
 * ```
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Suppress("unused")
abstract class BaseDialogFragment<VM : BaseViewModel, VDB : ViewDataBinding> : DialogFragment(),
    IView<VM> {

    var rootView: View? = null
        private set
    lateinit var viewModel: VM
        private set
    var viewDataBinding: VDB? = null
        private set
    val binding: VDB
        get() = viewDataBinding!!
    var progressDialog: Dialog? = null
        private set

    private val jumpDebounce by lazy {
        JumpDebounce()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = createRootView(inflater, container, savedInstanceState)
        if (isBinding()) {
            viewDataBinding = DataBindingUtil.bind(rootView!!)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding?.unbind()
    }

    /**
     * 创建 [rootView]
     */
    open fun createRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(getLayoutId(), container, false)
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

    /**
     * 获取Activity持有的[ViewModel]
     */
    fun <T : ViewModel> obtainActivityViewModel(vmClass: Class<T>): T {
        return ViewModelProvider(
            requireActivity().viewModelStore,
            requireActivity().defaultViewModelProviderFactory,
            requireActivity().defaultViewModelCreationExtras
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
        showToast(resources.stringResIdToString(resId))
    }

    /**
     * 显示进度对话框
     */
    fun showProgressDialog(
        layoutId: Int = R.layout.mvvmframe_progress_dialog,
        cancel: Boolean = false
    ) {
        dismissProgressDialog()
        progressDialog = BaseProgressDialog(requireContext()).also {
            it.setContentView(layoutId)
            it.setCanceledOnTouchOutside(cancel)
            it.show()
        }
    }

    /**
     * 关闭进度对话框
     */
    fun dismissProgressDialog() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

    @Suppress("DEPRECATION")
    @Deprecated("DeprecatedIsStillUsed")
    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        if (jumpDebounce.isIgnoreJump(intent)) {
            return
        }
        super.startActivityForResult(intent, requestCode, options)
    }

    fun <T : View> findViewById(@IdRes id: Int): T? {
        return rootView?.findViewById(id)
    }

    fun finish() {
        requireActivity().finish()
    }

}