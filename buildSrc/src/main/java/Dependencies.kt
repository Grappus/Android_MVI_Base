object Versions {
    //App
    const val COMPILE_SDK = 29
    const val MIN_SDK = 21
    const val TARGET_SDK = 29

    //Gradle
    const val GRADLE_BUILD_TOOL = "3.5.3"

    //Kotlin
    const val KOTLIN = "1.3.50"
    const val KOTLIN_CORE = "1.0.2"

    //Compat
    const val APP_COMPAT = "1.1.0"

    //Android Core
    const val VECTOR = "1.0.1"

    //Android Architecture
    const val LIFECYCLE = "2.0.0"
    const val NAVIGATION = "2.0.0"
    const val CONSTRAINT_LAYOUT = "2.0.0-beta2"

    //Room
    const val ROOM = "2.0.0"

    //Rx
    const val RX_JAVA = "2.2.0"
    const val RX_ANDROID = "2.0.2"
    const val RX_PERMISSIONS = "0.10.2"

    //Retrofit
    const val OK_HTTP = "3.11.0"
    const val RETROFIT = "2.4.0"

    //Dagger
    const val DAGGER = "2.17"

    //Logging
    const val TIMBER = "4.6.0"

    //Tests
    const val JUNIT = "4.12"
    const val JUNIT_EXT = "1.1.0"
    const val ESPRESSO_CORE = "3.1.1"
    const val MOCKITO = "2.8.9"
    const val MOCKITO_KOTLIN = "1.6.0"
    const val ANNOTATIONS = "1.0.0"

    //Code Style
    const val KT_LINT = "0.11.1"
}

object AndroidDependencies {
    //Gradle
    const val gradleBuildTool = "com.android.tools.build:gradle:${Versions.GRADLE_BUILD_TOOL}"

    //Kotlin
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    const val kotlinCore = "androidx.core:core-ktx:${Versions.KOTLIN_CORE}"

    //Compat
    const val appCompat = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

    //Android Core
    const val vectorDrawable = "androidx.vectordrawable:vectordrawable:${Versions.VECTOR}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    //Android Architecture
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.LIFECYCLE}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.LIFECYCLE}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION}"

    //Room
    const val roomRuntime = "android.arch.persistence.room:runtime:${Versions.ROOM}"

    //Rx
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.RX_JAVA}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.RX_ANDROID}"
}

object ExternalDependencies {
    //Logging
    const val timber = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    //Network calls
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.OK_HTTP}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.OK_HTTP}"

    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT}"
    const val retrofitGsonConvertor = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    //Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"

    //Rx
    const val rxPermissions = "com.github.tbruyelle:rxpermissions:${Versions.RX_PERMISSIONS}"
}

object TestDependencies {
    //Junit
    const val junit = "junit:junit:${Versions.JUNIT}"
    const val junitExt = "androidx.test.ext:junit:${Versions.JUNIT_EXT}"

    //Espresso
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"

    //mockito
    const val mockito = "org.mockito:mockito-core:${Versions.MOCKITO}"

    const val supportAnnotations = "androidx.annotation:annotation:${Versions.ANNOTATIONS}"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"

    //testing
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.OK_HTTP}"
}