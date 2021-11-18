package dependencies

object AnnotationProcessing {

    // Room components
    val room_compiler = "androidx.room:room-compiler:${Versions.room}"

    //dependency injection
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_compiler_versioin}"

    // Lifecycle components
    val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}"
}
