package com.king.frame.mvvmframe.plugin.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * 依赖相关扩展函数
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
internal fun Project.hasDependency(group: String, name: String): Boolean {
    return configurations.any { configuration ->
        configuration.dependencies.any { dependency ->
            dependency.group == group && dependency.name == name
        }
    }
}

/**
 * implementation
 */
internal fun DependencyHandler.implementation(
    group: String,
    artifact: String,
    version: String
) {
    add(IMPLEMENTATION, group, artifact, version)
}

/**
 * kapt
 */
internal fun DependencyHandler.kapt(
    group: String,
    artifact: String,
    version: String
) {
    add(KAPT, group, artifact, version)
}

/**
 * 添加依赖
 */
internal fun DependencyHandler.add(
    configurationName: String,
    group: String,
    artifact: String,
    version: String
) {
    add(configurationName, "$group:$artifact:$version")
}

private const val IMPLEMENTATION = "implementation"
private const val KAPT = "kapt"
