object Versions {
    //App
    const val COMPILE_SDK = 29
    const val MIN_SDK = 21
    const val TARGET_SDK = 29

    //Compat
    const val APP_COMPAT = "1.0.2"

    //Kotlin
    const val KOTLIN = "1.3.50"
    const val KOTLIN_CORE = "1.0.2"
    const val KOTLIN_VERSION = "1.3.50"
    const val BUILD_TOOLS_VERSION = "3.5.3"

    //logging
    const val TIMBER = "4.6.0"

    //room database
    const val ROOM = "2.0.0"

    // Rx
    const val RX_JAVA = "2.2.0"
    const val RX_ANDROID = "2.0.2"
    const val RX_PERMISSION = "0.10.2"

    //retrofit- notwork calls
    const val OKHTTP = "3.11.0"
    const val RETROFIT = "2.4.0"

    // dagger
    const val DAGGER = "2.17"

    //Tests
    const val JUNIT = "4.12"
    const val JUNIT_EXT = "1.1.0"
    const val ESPRESSO_CORE = "3.1.1"
    const val MOCKITO = "2.8.9"
    const val MOCKITO_KOTLIN = "1.6.0"
    const val ANNOTATIONS = "1.0.0"

    // Code Style
    const val KT_LINT = "0.11.1"
}

object AndroidDependencies {
    //Compat
    const val appCompat = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

    //Kotlin
    const val kotlinPlugin = "org.jetbrains.KOTLIN:KOTLIN-gradle-plugin:${Versions.KOTLIN}"
    const val kotlinLib = "org.jetbrains.KOTLIN:KOTLIN-stdlib-jdk7:${Versions.KOTLIN}"
    const val kotlinCore = "androidx.core:core-ktx:${Versions.KOTLIN_CORE}"
    const val kotlinGradlePlugin = "org.jetbrains.KOTLIN:KOTLIN-gradle-plugin:${Versions.KOTLIN_VERSION}"
    const val toolsBuildGradlePlugin = "com.android.tools.build:gradle:${Versions.BUILD_TOOLS_VERSION}"

    //Logging
    const val timber = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    //Database
    const val roomRuntime = "android.arch.persistence.room:runtime:${Versions.ROOM}"

    //Rx
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.RX_JAVA}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.RX_ANDROID}"

    //network calls
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT}"
    val retrofitGsonConvertor = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    //dagger
    const val dagger = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
}

object TestDependencies {
    //Junit
    const val junit = "JUNIT:JUNIT:${Versions.JUNIT}"
    const val junitExt = "androidx.test.ext:JUNIT:${Versions.JUNIT_EXT}"

    //Espresso
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"

    //mockito
    const val mockito = "org.mockito:mockito-core:${Versions.MOCKITO}"

    const val supportAnnotations = "androidx.annotation:annotation:${Versions.ANNOTATIONS}"
    val mockitoKotlin = "com.nhaarman:mockito-KOTLIN:${Versions.MOCKITO_KOTLIN}"

    //testing
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP}"
}