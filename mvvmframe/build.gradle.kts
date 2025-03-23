plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.king.frame.mvvmframe"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    // appcompat
    compileOnly(libs.androidx.appcompat)

    api(libs.androidx.core.ktx)
    api(libs.androidx.fragment.ktx)

    // okhttp
    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)

    // retrofit
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.gson)

    // retrofit-helper
    api(libs.retrofit.helper)

    // lifecycle
    api(libs.lifecycle.ktx)
    api(libs.lifecycle.viewmodel.ktx)

    // room
    api(libs.room.runtime)
    api(libs.room.ktx)
    ksp(libs.room.compiler)

    // hilt
    api(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    // log
    api(libs.timber)
    // toast
    implementation(libs.toaster)
}

kapt {
    correctErrorTypes = true
}
