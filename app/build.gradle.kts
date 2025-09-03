plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kspApp)
    alias(libs.plugins.parcelize)
    kotlin("plugin.serialization") version "2.0.21"
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hiltApp)
}

android {
    namespace = "com.example.dogedex"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dogedex"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.dogedex.CustomTestRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        dataBinding = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(project(":core"))
    implementation(project(":camera"))

    implementation(libs.androidx.lifecycle)

    //retrofit y moshi
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.moshi)
    implementation(libs.retrofit2.gson)

    //coil image
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.compose)

    //nav graph
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    //cameraX
    implementation(libs.androidx.camerax)
    implementation(libs.androidx.camerax.lifecycle)
    implementation(libs.androidx.camerax.view)

    //tensor flow lite
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)

    //Compose
    implementation(platform(libs.androidx.compose.boom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.founation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.viewmodel)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.icon.core)
    implementation(libs.androidx.compose.icon.extends)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.rules)
    implementation(libs.androidx.compose.runtime)
    androidTestImplementation(libs.androidx.compose.testing.unit)

    //Navigation Compose
    implementation(libs.androidx.navigation.compose)

    //hilt
    implementation(libs.hilt)
    implementation(libs.hilt.compose.navigation)
    ksp(libs.hilt.compiler)

    //espresso idlin
    implementation(libs.android.espresso.idlin)

    testImplementation(libs.coroutine.test)

    testImplementation(libs.junit)
    testImplementation(libs.mock.test)
    testImplementation(libs.arc.core)

    androidTestImplementation(libs.hilt.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.boom))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}