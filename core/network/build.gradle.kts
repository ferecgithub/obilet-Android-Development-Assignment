plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("dagger.hilt.android.plugin")
    kotlin("kapt") version "1.9.23"
}

android {
    namespace = "com.ferechamitebeyli.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        android.defaultConfig.buildConfigField("String", "BASE_URL", "\"http://sandbox.arabamd.com/\"")
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://v2-api.obilet.com/api/\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://v2-api.obilet.com/api/\"")
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
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    api(libs.retrofit2)
    api(libs.moshi)
    api(libs.moshiKotlin)
    api(libs.moshiConverter)
    kapt(libs.moshiKotlinCodegenKapt)

    implementation(libs.loggingInterceptor)
    implementation(libs.coroutinesAndroid)
    implementation(libs.coroutinesCore)

    implementation(libs.hiltAndroid)
    kapt(libs.hiltKapt)

    implementation(libs.androidx.junit.ktx)
}