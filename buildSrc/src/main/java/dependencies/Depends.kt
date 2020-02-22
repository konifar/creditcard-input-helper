package dependencies

@Suppress("unused")
object Depends {

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.0.0-alpha09"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
        const val mavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:2.1"
    }

    object Test {
        const val junit = "junit:junit:4.12"
        const val robolectric = "org.robolectric:robolectric:4.1"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta3"
        const val design = "com.google.android.material:material:1.1.0-rc01"

        object DataBinding {
            private const val version = "3.2.1"
            const val common = "androidx.databinding:databinding-common:$version"
            const val runtime = "androidx.databinding:databinding-runtime:$version"
        }
    }

    object Kotlin {
        const val version = "1.3.61"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    }

    object Timber {
        const val core = "com.jakewharton.timber:timber:4.7.1"
    }
}