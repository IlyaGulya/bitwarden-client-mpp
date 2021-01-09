buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://dl.bintray.com/icerockdev/plugins")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
        classpath("com.android.tools.build:gradle:3.5.4")
        classpath("dev.icerock.moko:network-generator:0.8.0")
        classpath("org.jetbrains.compose:compose-gradle-plugin:0.3.0-build139")
    }
}
group = "me.gulya.bitwarden"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}