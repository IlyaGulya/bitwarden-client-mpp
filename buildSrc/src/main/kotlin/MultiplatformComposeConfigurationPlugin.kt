import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class MultiplatformComposeConfigurationPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        listOf(
            "com.android.library",
            "kotlin-multiplatform",
            "org.jetbrains.compose"
        ).forEach { pluginClass ->
            project.pluginManager.apply(pluginClass)
        }

        project.extensions.getByType(KotlinMultiplatformExtension::class).run {
            jvm("desktop")

            android()

            sourceSets {
                named("commonMain") {
                    dependencies {
                        implementation(compose.runtime)
                        implementation(compose.foundation)
                        implementation(compose.material)
                    }
                }

                named("androidMain") {
                    dependencies {
                        implementation("androidx.appcompat:appcompat:1.1.0")
                        implementation("androidx.core:core-ktx:1.3.1")
                    }
                }

                named("desktopMain") {
                    dependencies {
                        implementation(compose.desktop.common)
                    }
                }
            }

            project.tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = "1.8"
                    freeCompilerArgs = freeCompilerArgs + listOf("-Xallow-jvm-ir-dependencies")
                }
            }

        }

    }

}