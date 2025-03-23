package com.king.frame.mvvmframe.plugin

import com.king.frame.mvvmframe.plugin.internal.PluginId
import com.king.frame.mvvmframe.plugin.internal.Version
import com.king.frame.mvvmframe.plugin.internal.hasDependency
import com.king.frame.mvvmframe.plugin.internal.implementation
import com.king.frame.mvvmframe.plugin.internal.kapt
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * MVVMFrame插件
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class MvvmFramePlugin : Plugin<Project> {

    override fun apply(target: Project) {

        val mvvmFrame = target.extensions.create(MVVM_FRAME, MvvmFrameExtension::class.java)

        target.pluginManager.apply {
            if (!hasPlugin(PluginId.KOTLIN_ANDROID)) {
                apply(PluginId.KOTLIN_ANDROID)
            }
            if (!hasPlugin(PluginId.KOTLIN_KAPT)) {
                apply(PluginId.KOTLIN_KAPT)
            }
            if (!hasPlugin(PluginId.HILT_ANDROID)) {
                apply(PluginId.HILT_ANDROID)
            }
        }

        target.plugins.withId(PluginId.HILT_ANDROID) {
            target.dependencies.apply {
                implementation(DAGGER_GROUP, HILT_ANDROID, Version.HILT_VERSION)
                kapt(DAGGER_GROUP, HILT_COMPILER, Version.HILT_VERSION)
            }
        }

        target.afterEvaluate {
            target.dependencies.apply {
                val roomVersion = mvvmFrame.roomVersion
                if (mvvmFrame.enabledRoomRuntime && !target.hasDependency(ROOM_GROUP, ROOM_RUNTIME)) {
                    implementation(ROOM_GROUP, ROOM_RUNTIME, roomVersion)
                }
                if (mvvmFrame.enabledRoomCompiler && !target.hasDependency(ROOM_GROUP, ROOM_COMPILER)) {
                    kapt(ROOM_GROUP, ROOM_COMPILER, roomVersion)
                }
            }
        }
    }

    companion object {
        private const val MVVM_FRAME = "mvvmFrame"

        private const val DAGGER_GROUP = "com.google.dagger"
        private const val HILT_ANDROID = "hilt-android"
        private const val HILT_COMPILER = "hilt-compiler"
        private const val ROOM_GROUP = "androidx.room"
        private const val ROOM_RUNTIME = "room-runtime"
        private const val ROOM_COMPILER = "room-compiler"
    }

}
