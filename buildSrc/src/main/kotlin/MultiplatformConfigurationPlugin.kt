import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class MultiplatformConfigurationPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        listOf(
            "com.android.library",
            "kotlin-multiplatform"
        ).forEach { pluginClass ->
            project.pluginManager.apply(pluginClass)
        }

        project.extensions.getByType(KotlinMultiplatformExtension::class).run {
            jvm("desktop")
            android()
            ios()

            sourceSets {
                named("commonTest") {
                    dependencies {
                        implementation(Deps.JetBrains.Kotlin.testCommon)
                        implementation(Deps.JetBrains.Kotlin.testAnnotationsCommon)
                    }
                }

                named("androidTest") {
                    dependencies {
                        implementation(Deps.JetBrains.Kotlin.testJunit)
                    }
                }
                named("desktopTest") {
                    dependencies {
                        implementation(Deps.JetBrains.Kotlin.testJunit)
                    }
                }
            }

            project.tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = "11"
                    languageVersion = "1.4"
                    freeCompilerArgs = freeCompilerArgs + listOf("-Xallow-jvm-ir-dependencies")
                }
            }
        }
    }

}