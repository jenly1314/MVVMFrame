plugins {
    `kotlin-dsl`
    alias(libs.plugins.maven.publish)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(gradleApi())
    implementation(libs.dagger.hilt.android.plugin)
    implementation(libs.javapoet)
}

gradlePlugin {
    plugins {
        create("mvvmFramePlugin") { // 插件配置
            id = "com.github.jenly1314.mvvmframe" // 插件 ID
            implementationClass = "com.king.frame.mvvmframe.plugin.MvvmFramePlugin" // 插件实现类
            displayName = "MvvmFramePlugin"
        }
    }
}
