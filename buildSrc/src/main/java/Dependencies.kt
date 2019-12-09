object Versions {

    //App
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29

    //Compat
    const val appCompat = "1.0.2"

    //Kotlin
    const val kotlin = "1.3.50"
    const val kotlinCore = "1.0.2"

    //Tests
    const val junit = "4.12"
    const val junitExt = "1.1.0"
    const val espressoCore = "3.1.1"

}

object AndroidDependencies {

    //Compat
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

    //Kotlin
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCore = "androidx.core:core-ktx:${Versions.kotlinCore}"
}

object TestDependencies {

    //Junit
    const val junit = "junit:junit:${Versions.junit}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"

    //Espresso
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}