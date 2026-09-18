package com.king.frame.mvvmframe.config

/**
 * MVVMFrame全局配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
abstract class FrameConfigModule : AppliesOptions {
    /**
     * 是否启用解析配置
     * @return 默认返回{@code true}
     */
    open fun isManifestParsingEnabled(): Boolean {
        return true
    }
}