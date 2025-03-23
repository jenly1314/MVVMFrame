package com.king.frame.mvvmframe.base.controller

/**
 * 加载控制器；默认实现请查看[DefaultLoadingController]
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
interface LoadingController : Controller {

    /**
     * 显示加载中
     */
    fun showLoading()

    /**
     * 隐藏加载中
     */
    fun hideLoading()

}
