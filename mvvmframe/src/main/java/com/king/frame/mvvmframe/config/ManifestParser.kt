package com.king.frame.mvvmframe.config

import android.content.Context
import android.content.pm.PackageManager
import com.king.logx.LogX

/**
 * Manifest解析器；实现参考 [ManifestParse](https://github.com/bumptech/glide/blob/f7d860412f061e059aa84a42f2563a01ac8c303b/library/src/main/java/com/bumptech/glide/module/ManifestParser.java)
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 * <p>
 */
class ManifestParser(private val context: Context) {

    /**
     * 解析
     */
    @Suppress("DEPRECATION")
    fun parse(): List<FrameConfigModule> {
        LogX.d("Loading MVVMFrame modules")
        val modules: MutableList<FrameConfigModule> = ArrayList()
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            if (appInfo.metaData == null) {
                LogX.d("Got null app info metadata")
                return modules
            }
            LogX.v("Got app info metadata: " + appInfo.metaData)
            for (key in appInfo.metaData.keySet()) {
                if (CONFIG_MODULE_VALUE == appInfo.metaData[key]) {
                    modules.add(parseModule(key))
                    LogX.d("Loaded MVVMFrame module: $key")
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse FrameConfigModules", e)
        }
        LogX.d("Finished loading MVVMFrame modules")
        return modules
    }

    private fun parseModule(className: String): FrameConfigModule {
        val clazz = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Unable to find FrameConfigModule implementation", e)
        }
        val module = try {
            clazz.getDeclaredConstructor().newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException(
                "Unable to instantiate FrameConfigModule implementation for $clazz",
                e
            )
        } catch (e: IllegalAccessException) {
            throw RuntimeException(
                "Unable to instantiate FrameConfigModule implementation for $clazz",
                e
            )
        }
        if (module !is FrameConfigModule) {
            throw RuntimeException("Expected instanceof FrameConfigModule, but found: $module")
        }
        return module
    }

    companion object {
        private const val CONFIG_MODULE_VALUE = "FrameConfigModule"
    }
}
