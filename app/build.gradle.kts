import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val key: String = gradleLocalProperties(rootDir).getOrDefault("encryptionKey", "\"default-key\"") as String
val apiKey: String = gradleLocalProperties(rootDir).getOrDefault("apiKey", "\"default-key\"") as String

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.parcelize)
    id(Plugins.hilt)
}




android {
    compileSdk = 31
    namespace = "com.example.europeanmap"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    defaultConfig {
        applicationId = "com.example.europeanmap"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "encryptionKey", key)
            buildConfigField("String", "apiKey", apiKey)

        }
        getByName("release") {
            isMinifyEnabled = false
            buildConfigField("String", "encryptionKey", key)
            buildConfigField("String", "apiKey", apiKey)

        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}


dependencies {
    implementation(Kotlin.core)
    implementation(Compose.ui)
    implementation(Compose.uiTooling)
    implementation(Compose.livedata)
    implementation(Compose.foundation)
    implementation(Compose.material)
    implementation(Compose.materialIconsCore)
    implementation(Compose.materialIconExtended)
    implementation(Compose.accompanistPermission)
    implementation("androidx.lifecycle:lifecycle-process:2.4.1")
    implementation("androidx.databinding:databinding-runtime:4.2.2")
    androidTestImplementation(Compose.composeUiTest)
    implementation(Compose.navigation)

    implementation(Timber.library)
    implementation(Navigation.navigation)
    implementation(Navigation.navigationUI)

    implementation(Kotlin.coroutines)
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutineReactive)


    testImplementation(Test.coreTesting)
    testImplementation(Test.core)
    testImplementation(Test.coroutineTest)
    testImplementation(Test.robolectric)
    testImplementation(Test.mockk)
    testImplementation(Test.extJUnit)
    testImplementation(Test.espressoCore)
    testImplementation(Test.junit)


    // Hilt
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltAndroidCompiler)
    kapt(Hilt.hiltCompiler)
    implementation(Hilt.hiltNavigation)

    //Room

    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)
    implementation(Room.sqliteCipher)
    implementation("io.reactivex.rxjava3:rxjava:3.1.1")
    //Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okhttp)
    implementation(Retrofit.gson)
    implementation(Retrofit.logging)

    implementation(Compose.coil)
    implementation(Compose.swipeToRefresh)
    implementation(Maps.compose)
    implementation(Maps.services)
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha03")
}