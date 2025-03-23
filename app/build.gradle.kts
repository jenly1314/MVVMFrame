plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.mvvmframe)
}

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

    buildFeatures{
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

    ksp(libs.room.compiler)

    // MVVMFrame
    implementation(project(":mvvmframe"))

}

mvvmFrame {
    //..
}

kapt {
    correctErrorTypes =  true
}
