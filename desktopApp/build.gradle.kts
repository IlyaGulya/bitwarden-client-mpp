import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
            kotlinOptions.useIR = true
            kotlinOptions.freeCompilerArgs = listOf(
                *kotlinOptions.freeCompilerArgs.toTypedArray(),
                "-Xallow-jvm-ir-dependencies",
                "-Xskip-prerelease-check"
            )

        }
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "DesktopAppKt"
    }
}