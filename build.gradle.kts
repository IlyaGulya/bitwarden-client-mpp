buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        repositories {
            maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("com.android.tools.build:gradle:3.5.2")
        classpath("dev.icerock.moko:network-generator:0.8.0")
    }
}
group = "me.gulya.bitwarden"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
    }
}
