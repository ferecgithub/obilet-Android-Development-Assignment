plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("dagger.hilt.android.plugin")
    kotlin("kapt") version "1.9.23"
}

android {
    namespace = "com.ferechamitebeyli.obiletandroidassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ferechamitebeyli.obiletandroidassignment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0.0"

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
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":core:caching"))
    implementation(project(":core:network"))
    implementation(project(":core:navigation"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
    implementation(project(":feature:journey"))
    implementation(project(":feature:splash"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.hiltAndroid)
    kapt(libs.hiltKapt)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}