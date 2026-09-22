import java.util.Properties // ← ADDED

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

// --- ADDED: Read local.properties ---
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}
// -------------------------------------

android {
    namespace = "com.example.movieapp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.movieapp"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
            // --- ADDED ---
            buildConfigField(
                "String",
                "TMDB_API_KEY",
                "\"${localProperties.getProperty("TMDB_API_KEY", "")}\""
            )
            // --------------
        }
        // --- ADDED: debug build type ---
        debug {
            buildConfigField(
                "String",
                "TMDB_API_KEY",
                "\"${localProperties.getProperty("TMDB_API_KEY", "")}\""
            )
        }
        // --------------------------------
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true // ← ADDED: Required to generate BuildConfig class
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-compiler:2.60.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson
    implementation("com.google.code.gson:gson:2.11.0")

    // Gson converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")


    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
// Use latest version
// If you are using Compose, you might also need this for viewModel() helper
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    implementation("io.coil-kt:coil-compose:2.6.0")

    implementation("androidx.compose.material:material-icons-extended")



    // Paging3
    implementation("androidx.paging:paging-runtime-ktx:3.3.2")
    implementation("androidx.paging:paging-compose:3.3.2")

    val roomVersion = "2.8.4"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
}