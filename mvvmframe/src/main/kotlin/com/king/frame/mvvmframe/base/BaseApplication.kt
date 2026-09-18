package com.king.frame.mvvmframe.base

import android.app.Application
import android.view.Gravity
import com.hjq.toast.Toaster

/**
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * 你需要参照如下方式添加 @HiltAndroidApp 注解
 *
 * ```
 * // 示例
 * @HiltAndroidApp
 * class YourApplication : BaseApplication() {
 *
 * }
 * ```
 * PS：如果由于某种原因，导致你不能继承[BaseApplication]；你也可以在你自定义的Application的onCreate函
 * 数中通过调用[BaseApplication.initAppConfig]来进行初始化
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAppConfig(this)
    }

    companion object {
        /**
         * 初始化App配置
         */
        @JvmStatic
        fun initAppConfig(application: Application) {
            // 初始化Toaster
            Toaster.init(application)
            val offsetY = application.resources.displayMetrics.heightPixels / 6
            Toaster.setGravity(Gravity.BOTTOM, 0, offsetY)
        }
    }
}
