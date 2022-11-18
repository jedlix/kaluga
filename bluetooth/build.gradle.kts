plugins {
    kotlin("multiplatform")
    id("jacoco")
    id("convention.publication")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("kotlinx-atomicfu")
}

publishableComponent()

dependencies {
    implementationDependency(Dependencies.BLEScanner)
    implementation(project(":location", ""))
    implementationDependency(Dependencies.KotlinX.AtomicFu)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":bluetooth-permissions", ""))
            }
        }
        commonTest {
            dependencies {
                implementation(project(":test-utils-bluetooth", ""))
            }
        }
    }
}
