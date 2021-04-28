package com.king.frame.mvvmframe.config;


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class FrameConfigModule implements AppliesOptions {

    /**
     * 是否启用解析配置
     * @return 默认返回{@code true}
     */
    public boolean isManifestParsingEnabled() {
        return true;
    }

}
