plugins {
    `kotlin-dsl`
    alias(libs.plugins.maven.publish)
}

val javaVersion = 17

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

dependencies {
    implementation(gradleApi())
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.android)
    implementation(libs.kotlin.kapt)
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
