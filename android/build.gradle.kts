plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    `maven-publish`
}

android {
    namespace = "info.wiseSpend"
    compileSdk = findProperty("android.compileSdk").toString().toInt()

    defaultConfig {
        minSdk = findProperty("android.minSdk").toString().toInt()
        targetSdk = findProperty("android.targetSdk").toString().toInt()
        applicationId = "info.spend"
        versionCode = 1
        versionName = "0.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.decompose)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("debugApk") {
                groupId = "info.javaway.wisespend"
                artifactId = "myapp-debug-apk"
                version = "1.0"

                val apkDebug = layout.buildDirectory.file("outputs/apk/debug/android-debug.apk")
                artifact(apkDebug.get().asFile) {
                    extension = "apk"
                }
            }
        }

        repositories {
            mavenLocal()
        }
    }

    tasks.named("publishDebugApkPublicationToMavenLocal").get().dependsOn("assembleDebug")
}