plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()

    androidTarget()

    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            export(libs.decompose)
            export((libs.lifecycle))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)

                //settings
                implementation(libs.multiplatform.settings)

                //di
                api(libs.koin.core)

                //dateTime
                implementation(libs.dateTime)

                //db
                implementation(libs.sqldelight.coroutines.extensions)

                //ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)

                //Logs
                api(libs.napier)

                //decompose
                api(libs.decompose)
                api(libs.decompose.extensions.compose)
                api(libs.lifecycle)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test-annotations-common"))

            implementation(libs.assertk)
            implementation(libs.kotlin.coroutines.test)
        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.desktop.driver)
            implementation(libs.kotlinx.coroutines.swing)
        }

        val iosArm64Main by getting
        val iosX64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosX64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }

        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    namespace = "info.javaway.wiseSpend"
    compileSdk = findProperty("android.compileSdk").toString().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("AppDb") {
            packageName.set("info.javaway.wiseSpend.db")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/db"))
        }
    }
}