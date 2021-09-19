import org.gradle.api.artifacts.dsl.DependencyHandler

object Libs {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7"
    const val KOTLIN_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core"
    const val APPCOMPAT = "androidx.appcompat:appcompat"
    const val CORE_KTX = "androidx.core:core-ktx"
    const val LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx"
    const val HILT = "com.google.dagger:hilt-android"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx"
    const val MATERIAL = "com.google.android.material:material"
    const val VIEW_BINDING_PROPEERTY_DELEGATE =
        "com.github.kirich1409:viewbindingpropertydelegate"
    const val RETROFIT = "com.squareup.retrofit2:retrofit"
    const val RETROFIT_CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi"
    const val OKHTTP_BOM = "com.squareup.okhttp3:okhttp-bom"
    const val OKHTTP = "com.squareup.okhttp3:okhttp"
    const val OKHTTP_LOGGER_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor"
    const val ROOM = "androidx.room:room-ktx"
    const val ROOM_RUNTIME = "androidx.room:room-runtime"
    const val ROOM_COMPILER = "androidx.room:room-compiler"
    const val SHIMMER = "com.facebook.shimmer:shimmer"
    const val PAGING = "androidx.paging:paging-runtime"
    const val GLIDE = "com.github.bumptech.glide:glide"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler"
    const val ANDROID_SVG = "com.caverock:androidsvg-aar"
    const val MPANDROID_CHART = "com.github.philjay:mpandroidchart"

}

fun DependencyHandler.implementRetrofit() {
    add("implementation", Libs.RETROFIT)
    add("implementation", Libs.RETROFIT_CONVERTER_MOSHI)
    // OkHttp
    add("implementation", platform(Libs.OKHTTP_BOM))
    add("implementation", Libs.OKHTTP)
    add("implementation", Libs.OKHTTP_LOGGER_INTERCEPTOR)
}

fun DependencyHandler.implementRoom() {
    add("implementation", Libs.ROOM)
    add("implementation", Libs.ROOM_RUNTIME)
    add("kapt", Libs.ROOM_COMPILER)
}

fun DependencyHandler.implementHilt() {
    add("implementation", Libs.HILT)
    add("kapt", Libs.HILT_COMPILER)
}

fun DependencyHandler.implementKotlin() {
    add("implementation", Libs.KOTLIN)
    add("implementation", Libs.KOTLIN_COROUTINES)
}

fun DependencyHandler.implementGlide() {
    add("implementation", Libs.GLIDE)
    add("kapt", Libs.GLIDE_COMPILER)
}