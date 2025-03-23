package com.king.frame.mvvmframe.plugin

import com.king.frame.mvvmframe.plugin.internal.Version

/**
 * mvvmFrame插件配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
open class MvvmFrameExtension {
    /**
     * room版本
     */
    var roomVersion = Version.ROOM_VERSION

    /**
     * 是否启用依赖：androidx.room:room-runtime
     */
    var enabledRoomRuntime = true

    /**
     * 是否启用依赖：androidx.room:room-compiler
     */
    var enabledRoomCompiler = true
}
