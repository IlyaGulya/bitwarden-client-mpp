object Deps {

    object JetBrains {
        object Kotlin {
            // __KOTLIN_COMPOSE_VERSION__
            private const val VERSION = "1.4.21-2"
            const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"

            object Serialization {
                private const val VERSION_RUNTIME = "1.0.0"

                const val gradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:$VERSION"
                const val runtime = "org.jetbrains.kotlinx:kotlinx-serialization-json:$VERSION_RUNTIME"
            }

            const val testCommon = "org.jetbrains.kotlin:kotlin-test-common:$VERSION"
            const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$VERSION"
            const val testAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:$VERSION"
        }

        object Compose {
            // __LATEST_COMPOSE_RELEASE_VERSION__
            private const val VERSION = "0.3.0-build146"
            const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$VERSION"
        }
    }

    object Android {
        object Tools {
            object Build {
                const val gradlePlugin = "com.android.tools.build:gradle:4.0.1"
            }
        }
    }

    object AndroidX {
        object AppCompat {
            const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        }
    }

    object ArkIvanov {
        object MVIKotlin {
            private const val VERSION = "2.0.0"
            const val rx = "com.arkivanov.mvikotlin:rx:$VERSION"
            const val mvikotlin = "com.arkivanov.mvikotlin:mvikotlin:$VERSION"
            const val mvikotlinMain = "com.arkivanov.mvikotlin:mvikotlin-main:$VERSION"
            const val mvikotlinMainIosX64 = "com.arkivanov.mvikotlin:mvikotlin-main-iosx64:$VERSION"
            const val mvikotlinMainIosArm64 = "com.arkivanov.mvikotlin:mvikotlin-main-iosarm64:$VERSION"
            const val mvikotlinLogging = "com.arkivanov.mvikotlin:mvikotlin-logging:$VERSION"
            const val mvikotlinTimeTravel = "com.arkivanov.mvikotlin:mvikotlin-timetravel:$VERSION"
            const val mvikotlinExtensionsReaktive = "com.arkivanov.mvikotlin:mvikotlin-extensions-reaktive:$VERSION"
            const val mvikotlinExtensionsCoroutines = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$VERSION"
        }

        object Decompose {
            private const val VERSION = "0.1.5"
            const val decompose = "com.arkivanov.decompose:decompose:$VERSION"
            const val decomposeIosX64 = "com.arkivanov.decompose:decompose-iosx64:$VERSION"
            const val decomposeIosArm64 = "com.arkivanov.decompose:decompose-iosarm64:$VERSION"
            const val extensionsCompose = "com.arkivanov.decompose:extensions-compose-jetbrains:$VERSION"
        }
    }

    object Badoo {
        object Reaktive {
            private const val VERSION = "1.1.19"
            const val reaktive = "com.badoo.reaktive:reaktive:$VERSION"
            const val reaktiveTesting = "com.badoo.reaktive:reaktive-testing:$VERSION"
            const val utils = "com.badoo.reaktive:utils:$VERSION"
            const val coroutinesInterop = "com.badoo.reaktive:coroutines-interop:$VERSION"
        }
    }

    object Ktor {
        private const val VERSION = "1.4.1"

        object Client {
            const val okHttp = "io.ktor:ktor-client-okhttp:$VERSION"
            const val core = "io.ktor:ktor-client-core:$VERSION"
            const val serialization = "io.ktor:ktor-client-serialization:$VERSION"
            const val logging = "io.ktor:ktor-client-logging:$VERSION"
        }
    }

    object Klock {
        private const val VERSION = "1.12.1"
        const val klock = "com.soywiz.korlibs.klock:klock:$VERSION"
    }

    object Krypto {
        private const val VERSION = "2.0.0-alpha"
        const val krypto = "com.soywiz.korlibs.krypto:krypto:$VERSION"
    }

    object Napier {
        private const val VERSION = "1.4.1"
        const val napier = "com.github.aakira:napier:$VERSION"
    }

    object Okio {
        private const val VERSION = "2.9.0"
        const val okio = "com.squareup.okio:okio:$VERSION"
    }

    object BouncyCastle {
        private const val VERSION = "1.66"
        const val jdk15to18provider = "org.bouncycastle:bcprov-jdk15to18:$VERSION"
    }

    object MultiplatformSettings {
        private const val VERSION = "0.7.1"
        const val settings = "com.russhwolf:multiplatform-settings:$VERSION"
        const val settingsNoArg = "com.russhwolf:multiplatform-settings-no-arg:$VERSION"
    }

    object Squareup {
        object SQLDelight {
            private const val VERSION = "1.4.4"

            const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$VERSION"
            const val androidDriver = "com.squareup.sqldelight:android-driver:$VERSION"
            const val sqliteDriver = "com.squareup.sqldelight:sqlite-driver:$VERSION"
            const val nativeDriver = "com.squareup.sqldelight:native-driver:$VERSION"
        }
    }
}
