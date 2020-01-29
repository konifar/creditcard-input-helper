package dependencies

@Suppress("unused")
object Depends {

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.0.0-alpha09"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        const val kotlinSerialization =
            "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
    }

    object Test {
        const val junit = "junit:junit:4.12"
        const val testRunner = "androidx.test:runner:1.3.0-alpha02"
        const val testRules = "androidx.test:rules:1.3.0-alpha02"
        const val testCoreKtx = "androidx.test:core-ktx:1.2.1-alpha02"
        const val androidJunit4Ktx = "androidx.test.ext:junit-ktx:1.1.2-alpha02"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta3"
        const val design = "com.google.android.material:material:1.1.0-rc01"
        const val coreKtx = "androidx.core:core-ktx:1.2.0-rc01"
        const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.0"

        object DataBinding {
            private const val version = "3.2.1"
            const val common = "androidx.databinding:databinding-common:$version"
            const val runtime = "androidx.databinding:databinding-runtime:$version"
        }

        object Lifecycle {
            const val liveData = "androidx.lifecycle:lifecycle-livedata:2.2.0"
            const val liveDataCoreKtx = "androidx.lifecycle:lifecycle-livedata-core-ktx:2.2.0"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
            const val extension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
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