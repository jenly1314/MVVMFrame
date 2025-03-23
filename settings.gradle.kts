pluginManagement {

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
//        maven { url = uri("https://maven.aliyun.com/repository/central") }
//        maven { url = uri("https://maven.aliyun.com/repository/google") }
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }

    }
}
rootProject.name = "MVVMFrame"
include(":app")
include(":mvvmframe")
include(":mvvmframe-plugin")

