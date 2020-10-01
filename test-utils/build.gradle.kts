plugins {
    kotlin("multiplatform")
    id("jacoco")
    id("maven-publish")
    id("com.android.library")
}

val ext =  (gradle as ExtensionAware).extra

apply(from = "../gradle/publishable_component.gradle")

group = "com.splendo.kaluga"
version = ext["library_version"]!!
val kotlinx_coroutines_version = ext["kotlinx_coroutines_version"]!!

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                val ext =  (gradle as ExtensionAware).extra

                // these are not coming from component.gradle because they need to be in the main scope
                api(kotlin("test"))
                api(kotlin("test-junit"))
                implementation(project(":permissions", ""))
                implementation(project(":base", ""))
                implementation(project(":logging", ""))
                implementation("co.touchlab:stately-common:${ext["stately_version"]}")
                implementation("co.touchlab:stately-concurrency:${ext["stately_version"]}")
            }
        }
        getByName("jsMain") {
            dependencies {
                api(kotlin("test-js"))
            }
        }
    }
}

android {
    dependencies {
        val ext =  (gradle as ExtensionAware).extra
        api("org.jetbrains.kotlinx:kotlinx-coroutines-test:${ext["kotlinx_coroutines_version"]}")
    }
}
