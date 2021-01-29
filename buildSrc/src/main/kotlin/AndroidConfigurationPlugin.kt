import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.plugins.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidConfigurationPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.pluginManager.apply(LibraryPlugin::class)

        project.extensions.getByType(LibraryExtension::class).run {
            compileSdkVersion(30)

            defaultConfig {
                minSdkVersion(23)
                targetSdkVersion(30)
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            sourceSets {
                named("main") {
                    manifest.srcFile("src/androidMain/AndroidManifest.xml")
                    res.srcDirs("src/androidMain/res")
                }
            }

        }
    }

}