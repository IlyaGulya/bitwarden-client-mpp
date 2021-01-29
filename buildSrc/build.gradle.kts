plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath(Deps.JetBrains.Compose.gradlePlugin)
        classpath(Deps.JetBrains.Kotlin.kotlinGradlePlugin)
        classpath(Deps.JetBrains.Kotlin.kotlinSerializationGradlePlugin)
        classpath(Deps.Android.Tools.Build.gradlePlugin)
    }
}



dependencies {
    implementation(Deps.JetBrains.Compose.gradlePlugin)
    implementation(Deps.JetBrains.Kotlin.kotlinGradlePlugin)
    implementation(Deps.Android.Tools.Build.gradlePlugin)
    implementation(Deps.Squareup.SQLDelight.gradlePlugin)
}

kotlin {
    // Add Deps to compilation, so it will become available in main project
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}
