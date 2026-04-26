
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
//    id("com.github.jenly1314.mvvmframe")
}

val useMvvmFramePlugin = providers.gradleProperty("useMvvmFramePlugin").orNull?.toBoolean() == true

// 使用mvvmframe插件时可自定义配置
//if(useMvvmFramePlugin) {
//    mvvmFrame {
//        //..
//        // 是否使用KSP进行注解处理器的编译，默认为：false（即：使用KAPT）
//        useKsp = true
//    }
//}

android {
    namespace = "com.king.mvvmframe"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {

        applicationId = "com.king.mvvmframe"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = properties["VERSION_CODE"].toString().toInt()
        versionName = properties["VERSION_NAME"].toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    gradle.projectsEvaluated {
        tasks.withType<JavaCompile>().configureEach {
            options.compilerArgs.addAll(listOf("-Xmaxerrs", "500"))
        }
    }
}


dependencies {

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.swiperefreshlayout)
    implementation(libs.recyclerview)
    implementation(libs.constraintlayout)

    implementation(libs.base.util)
    implementation(libs.base.adapter)

    implementation(libs.android.ktx)

    implementation(libs.flowlayout)

    implementation(libs.logx)

    //leakCanary
    debugImplementation(libs.leakcanary)

    if (!useMvvmFramePlugin) {
        // room
        implementation(libs.room.runtime)
        implementation(libs.room.ktx)
        ksp(libs.room.compiler)

        // hilt
        implementation(libs.dagger.hilt.android)
        ksp(libs.dagger.hilt.compiler)
    }

    // MVVMFrame
    implementation(project(":mvvmframe"))

}


