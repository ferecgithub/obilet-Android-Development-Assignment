plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt") version "1.9.23"
}

android {
    namespace = "com.ferechamitebeyli.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(String(), "BASE_URL", "https://v2-api.obilet.com/api/")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.retrofit2)
    implementation(libs.moshi)
    implementation(libs.loggingInterceptor)
    implementation(libs.coroutinesAndroid)
    implementation(libs.coroutinesCore)

    implementation(libs.hiltAndroid)
    kapt(libs.hiltKapt)

    implementation(libs.androidx.junit.ktx)
}