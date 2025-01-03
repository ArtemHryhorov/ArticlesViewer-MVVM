plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.articles.viewer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.articles.viewer"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.androidx.room.compiler)
    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}