plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.10"
}
group = "me.gulya.bitwarden"
version = "1.0-SNAPSHOT"

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
    jvm {
        compilations.all {
            kotlinOptions {
                kotlinOptions.jvmTarget = "1.8"
                kotlinOptions.useIR = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
                api("com.soywiz.korlibs.klock:klock:1.12.1")
                api("io.ktor:ktor-client-core:1.4.1")
                api("io.ktor:ktor-client-serialization:1.4.1")
                api("io.ktor:ktor-client-logging:1.4.1")
                api("com.soywiz.korlibs.krypto:krypto:2.0.0-alpha")
                implementation("com.squareup.okio:okio:2.9.0")
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