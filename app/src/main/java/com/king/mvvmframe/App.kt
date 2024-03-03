package com.king.mvvmframe

import com.king.frame.mvvmframe.base.BaseApplication
import com.king.mvvmframe.app.Constants
import com.king.retrofit.retrofithelper.RetrofitHelper
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

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
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@HiltAndroidApp
class App : BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        initLogger()
        //------------------------------
        // 如果你没有使用FrameConfigModule中的第一中方式初始化BaseUrl，也可以通过第二种方式来设置BaseUrl（二选其一即可）
//        RetrofitHelper.getInstance().setBaseUrl(Constants.BASE_URL)
        // 设置动态BaseUrl
        RetrofitHelper.getInstance().apply {
            putDomain(Constants.DOMAIN_DICTIONARY, Constants.DICTIONARY_BASE_URL)
            putDomain(Constants.DOMAIN_JENLY, Constants.JENLY_BASE_URL)
        }
    }

    /**
     * 初始化[Logger]
     */
    private fun initLogger() {
        // 初始化日志打印
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .methodOffset(5)
            .tag(Constants.TAG)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    Logger.log(priority, tag, message, t)
                }
            }
        })
    }
}