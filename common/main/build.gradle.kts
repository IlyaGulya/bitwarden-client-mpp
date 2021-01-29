plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.21-2"
    id("org.jetbrains.compose")
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
    jvm {}
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
                api("com.soywiz.korlibs.klock:klock:1.12.1")
                api("io.ktor:ktor-client-core:1.4.1")
                api("io.ktor:ktor-client-serialization:1.4.1")
                api("io.ktor:ktor-client-logging:1.4.1")
                api("com.soywiz.korlibs.krypto:krypto:2.0.0-alpha")
                api("com.github.aakira:napier:1.4.1")
                implementation("com.squareup.okio:okio:2.9.0")

                implementation("com.arkivanov.decompose:decompose:0.1.5")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:0.1.5")

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api("org.bouncycastle:bcprov-jdk15to18:1.66")
            }
        }
        val jvmTest by getting
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }
    }
}