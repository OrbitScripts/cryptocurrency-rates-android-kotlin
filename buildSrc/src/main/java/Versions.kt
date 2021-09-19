object Versions {
    val versionName = "1.0.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    private val versionCodeBase = 1000 // XYYZZM; M = Module (tv, mobile)
    val versionCodeMobile = versionCodeBase + 3

    const val COMPILE_SDK = 31
    const val TARGET_SDK = 31
    const val MIN_SDK = 21

    const val ANDROID_GRADLE_PLUGIN = "7.0.2"
    const val KOTLIN = "1.5.31"
    const val HILT = "2.38.1"
}
