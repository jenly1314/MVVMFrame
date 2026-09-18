pluginManagement {

    val useMvvmFramePlugin = settings.providers.gradleProperty("useMvvmFramePlugin").orNull?.toBoolean() == true
    if(useMvvmFramePlugin) {
        val useLocalMvvmFramePlugin = settings.providers.gradleProperty("useLocalMvvmFramePlugin").orNull?.toBoolean() == true
        val mvvmframePluginVersion =
            settings.providers.gradleProperty("VERSION_NAME").orNull
                ?: error("Missing VERSION_NAME in gradle.properties")

        if (useLocalMvvmFramePlugin) {
            includeBuild("mvvmframe-plugin")
        }

        plugins {
            if (!useLocalMvvmFramePlugin) {
                id("com.github.jenly1314.mvvmframe") version mvvmframePluginVersion
            }
        }
    }

    repositories {
//        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
//        maven { url = uri("https://maven.aliyun.com/repository/central") }
//        maven { url = uri("https://maven.aliyun.com/repository/google") }
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "MVVMFrame"
include(":app")
include(":mvvmframe")

val useLocalMvvmFramePlugin =
    providers.gradleProperty("useLocalMvvmFramePlugin").orNull?.toBoolean() == true

if (!useLocalMvvmFramePlugin) {
    include(":mvvmframe-plugin")
}

