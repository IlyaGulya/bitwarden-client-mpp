plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.10"
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
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
            kotlinOptions.jvmTarget = "11"
            kotlinOptions.useIR = true
        }
        withJava()
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation("com.github.ajalt.clikt:clikt:3.0.1")
                implementation("io.ktor:ktor-client-okhttp:1.4.1")
//                implementation("com.google.lanterna:lanterna:3.0.4")
            }
        }
        val jvmTest by getting
    }
}

application {
    mainClass.set("MainKt")
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}

//tasks {
//    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
//        mergeServiceFiles()
//        manifest {
//            attributes(mapOf("Main-Class" to "MainKt"))
//        }
//    }
//}
