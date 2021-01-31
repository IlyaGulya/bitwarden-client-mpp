plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose")
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common:main"))
                implementation(project(":common:login"))
                implementation(project(":common:utils"))
                implementation(project(":common:compose-ui"))
                implementation(compose.desktop.currentOs)
                implementation(Deps.ArkIvanov.Decompose.decompose)
                implementation(Deps.ArkIvanov.Decompose.extensionsCompose)
                implementation(Deps.ArkIvanov.MVIKotlin.mvikotlin)
                implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinMain)
                implementation(Deps.Badoo.Reaktive.reaktive)
                implementation(Deps.Badoo.Reaktive.coroutinesInterop)
                implementation(Deps.Ktor.Client.okHttp)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "me.gulya.bitwarden.app.desktop.DesktopAppKt"
    }
}