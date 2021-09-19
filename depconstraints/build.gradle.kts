plugins {
    id("java-platform")
}

val appcompat = "1.3.1"
val core = "1.6.0"
val lifecycle = "2.3.1"
val hilt = Versions.HILT
val activity = "1.3.1"
val fragment = "1.3.6"
val material = "1.5.0-alpha03"
val viewBindingPropertyDelegate = "1.5.0-beta02"
val kotlin = Versions.KOTLIN
val kotlinCoroutines = "1.5.2"
val datastore = "1.0.0"
val room = "2.3.0"
val retrofit = "2.9.0"
val okhttp = "4.9.1"
val shimmer = "0.5.0"
val paging = "3.0.1"
val pagingKtx = "2.1.2"
val glide = "4.12.0"
val androidSvg = "1.4"
val mpandroidChart = "3.1.0"

dependencies {
    constraints {
        api("${Libs.APPCOMPAT}:$appcompat")
        api("${Libs.CORE_KTX}:$core")
        api("${Libs.LIFECYCLE_VIEW_MODEL_KTX}:$lifecycle")
        api("${Libs.ACTIVITY_KTX}:$activity")
        api("${Libs.FRAGMENT_KTX}:$fragment")
        api("${Libs.MATERIAL}:$material")
        api("${Libs.VIEW_BINDING_PROPEERTY_DELEGATE}:$viewBindingPropertyDelegate")
        api("${Libs.KOTLIN}:$kotlin")
        api("${Libs.KOTLIN_COROUTINES}:$kotlinCoroutines")
        api("${Libs.RETROFIT}:$retrofit")
        api("${Libs.RETROFIT_CONVERTER_MOSHI}:$retrofit")
        api("${Libs.OKHTTP_BOM}:$okhttp")
        api("${Libs.OKHTTP}:$okhttp")
        api("${Libs.OKHTTP_LOGGER_INTERCEPTOR}:$okhttp")
        api("${Libs.ROOM}:$room")
        api("${Libs.ROOM_RUNTIME}:$room")
        api("${Libs.ROOM_COMPILER}:$room")
        api("${Libs.HILT}:$hilt")
        api("${Libs.HILT_COMPILER}:$hilt")
        api("${Libs.SHIMMER}:$shimmer")
        api("${Libs.PAGING}:$paging")
        api("${Libs.GLIDE}:$glide")
        api("${Libs.GLIDE_COMPILER}:$glide")
        api("${Libs.ANDROID_SVG}:$androidSvg")
        api("${Libs.MPANDROID_CHART}:$mpandroidChart")
    }
}
