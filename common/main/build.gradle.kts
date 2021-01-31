plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization") version "1.4.21-2"
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
    repositories {
        maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Deps.JetBrains.Kotlin.Serialization.runtime)
                api(Deps.Klock.klock)
                api(Deps.Ktor.Client.core)
                api(Deps.Ktor.Client.serialization)
                api(Deps.Ktor.Client.logging)
                api(Deps.Krypto.krypto)
                api(Deps.Napier.napier)
                api(Deps.Badoo.Reaktive.reaktive)
                api(Deps.MultiplatformSettings.settings)
                api(Deps.MultiplatformSettings.settingsNoArg)
                implementation(Deps.Okio.okio)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by creating {
            dependsOn(commonMain)
            dependencies {
                api(Deps.BouncyCastle.jdk15to18provider)
            }
        }
        val androidMain by getting { dependsOn(jvmMain) }
        val desktopMain by getting { dependsOn(jvmMain) }
        val jvmTest by creating {
            dependsOn(commonTest)
        }
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }
    }
}