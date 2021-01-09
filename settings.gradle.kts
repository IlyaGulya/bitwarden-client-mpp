pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android" || requested.id.name == "kotlin-android-extensions") {
                useModule("com.android.tools.build:gradle:3.5.2")
            }
        }
    }
}
rootProject.name = "BitwardenMultiplatform"


include(":shared")
include(":androidApp")
include(":cliApp")
include(":desktopApp")