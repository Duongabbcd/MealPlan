plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.ezt.meal.ai.scan"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.ezt.meal.ai.scan"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("keystore/key_store_new.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding { enable = true }
        buildConfig = true
        dataBinding = true
    }

    base {
        archivesName.set("MealPlan_${defaultConfig.versionName}")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.viewbinding)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.firebase.config)

    // Retrofit 2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //UI dimen dp generator
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${libs.versions.coroutines.get()}")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.9.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.5")
    implementation("androidx.fragment:fragment-ktx:1.8.9")

    implementation("com.squareup.picasso:picasso:2.71828")

    // Hilt 2.55
    implementation("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-compiler:2.55")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")

    // Room + KSP
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Glide + KSP
    implementation("com.github.bumptech.glide:glide:4.15.1")
    ksp("com.github.bumptech.glide:compiler:4.15.1")

    //UI dimen dp generator
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    //advertisement
    implementation("com.facebook.android:facebook-android-sdk:18.0.2")

    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics-ktx:22.5.0")
    implementation("com.google.firebase:firebase-messaging")

    implementation(libs.adjust.android)
    implementation("com.android.installreferrer:installreferrer:2.2")

    implementation("com.google.ads.mediation:pangle:7.8.0.8.0")
    implementation("com.google.ads.mediation:applovin:13.5.1.0")
    implementation("com.google.ads.mediation:facebook:6.21.0.0")
    implementation("com.google.ads.mediation:vungle:7.6.2.0")
    implementation("com.google.ads.mediation:mintegral:17.0.21.0")

    implementation("com.google.android.gms:play-services-ads:24.9.0") // or latest
    implementation ("com.airbnb.android:lottie:6.6.6")

    implementation("androidx.camera:camera-camera2:1.4.1")
    implementation("androidx.camera:camera-lifecycle:1.4.1")
    implementation("androidx.camera:camera-view:1.4.1")

    //ok http3
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")  // For Gson converter
    implementation("com.squareup.okhttp3:okhttp:4.9.0") // OkHttp for networking
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.media3:media3-datasource-okhttp:1.7.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.15.1")// OkHttp integration


    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")
}