package com.king.frame.mvvmframe.plugin

import com.king.frame.mvvmframe.plugin.internal.Dependency
import com.king.frame.mvvmframe.plugin.internal.PluginId
import com.king.frame.mvvmframe.plugin.internal.hasDependency
import com.king.frame.mvvmframe.plugin.internal.implementation
import com.king.frame.mvvmframe.plugin.internal.kapt
import com.king.frame.mvvmframe.plugin.internal.ksp
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
        val useKsp = mvvmFrame.useKsp

        target.pluginManager.apply {
            if (!hasPlugin(PluginId.KOTLIN_ANDROID)) {
                apply(PluginId.KOTLIN_ANDROID)
            }
            if (!hasPlugin(PluginId.KOTLIN_KAPT)) {
                apply(PluginId.KOTLIN_KAPT)
            }
            if (useKsp) {
                if (!hasPlugin(PluginId.KOTLIN_KSP)) {
                    apply(PluginId.KOTLIN_KSP)
                }
            }
            if (!hasPlugin(PluginId.HILT_ANDROID)) {
                apply(PluginId.HILT_ANDROID)
            }
        }

        // Hilt plugin performs dependency checks during configuration; declare hilt deps before afterEvaluate.
        target.dependencies.apply {
            val hiltVersion = mvvmFrame.hiltVersion
            if (!target.hasDependency(Dependency.DAGGER_GROUP, Dependency.HILT_ANDROID)) {
                implementation(Dependency.DAGGER_GROUP, Dependency.HILT_ANDROID, hiltVersion)
            }

            if (!target.hasDependency(Dependency.DAGGER_GROUP, Dependency.HILT_COMPILER)) {
                if (useKsp) {
                    ksp(Dependency.DAGGER_GROUP, Dependency.HILT_COMPILER, hiltVersion)
                } else {
                    kapt(Dependency.DAGGER_GROUP, Dependency.HILT_COMPILER, hiltVersion)
                }
            }
        }

        target.afterEvaluate {
            target.dependencies.apply {
                val roomVersion = mvvmFrame.roomVersion
                if (mvvmFrame.enabledRoomRuntime && !target.hasDependency(Dependency.ROOM_GROUP, Dependency.ROOM_RUNTIME)) {
                    implementation(Dependency.ROOM_GROUP,Dependency.ROOM_RUNTIME, roomVersion)
                }
                if (mvvmFrame.enabledRoomCompiler && !target.hasDependency(Dependency.ROOM_GROUP, Dependency.ROOM_COMPILER)) {
                    if (useKsp) {
                        ksp(Dependency.ROOM_GROUP,Dependency.ROOM_COMPILER, roomVersion)
                    } else {
                        kapt(Dependency.ROOM_GROUP,Dependency.ROOM_COMPILER, roomVersion)
                    }
                }
            }
        }
    }

    companion object {
        private const val MVVM_FRAME = "mvvmFrame"
    }

}
