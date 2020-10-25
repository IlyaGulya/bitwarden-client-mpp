plugins {
    kotlin("jvm")
}

group = "me.gulya.bitwarden"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":shared"))
}
