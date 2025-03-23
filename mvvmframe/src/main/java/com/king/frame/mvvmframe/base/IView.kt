package com.king.frame.mvvmframe.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel

/**
 * 视图层定义一些共性需要实现的函数
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface IView<VM> {

    /**
     * 获取根布局ID
     * @return
     */
    @LayoutRes
    fun getLayoutId(): Int

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    fun initData(savedInstanceState: Bundle?)

    /**
     * 是否使用 ViewDataBinding
     */
    fun isBinding(): Boolean

    /**
     * 创建 [ViewModel]
     */
    fun createViewModel(): VM

}
