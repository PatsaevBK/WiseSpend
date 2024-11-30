rootProject.name = "SpendSense"
include("shared")
include("desktop")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        google()
    }
}