import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

private const val ANDROID_LIBRARY = "android-library"
private const val KOTLIN_LIBRARY = "kotlin-android"

class ModuleConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.applyPlugins()
        project.setProjectConfiguration()
    }

    private fun Project.applyPlugins() {
        apply {
            plugin(ANDROID_LIBRARY)
            plugin(KOTLIN_LIBRARY)
        }
    }

    private fun Project.setProjectConfiguration() {
        android().apply {
            compileSdk = ProjectConfig.compileSdk
            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile(ProjectConfig.defaultProguardFile),
                        ProjectConfig.proguardRulesPro
                    )
                }
            }
            buildFeatures {
                viewBinding = true
            }
        }
    }

    private fun Project.android(): LibraryExtension {
        return extensions.getByType(LibraryExtension::class.java)
    }
}