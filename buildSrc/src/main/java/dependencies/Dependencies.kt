package dependencies

import dependencies.Versions.hilt_lifecycle_viewmodel

object Dependencies {

    val kotlin_standard_library = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val kotlin_coroutines_play_services = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines_play_services}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"

    val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_components}"
    val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_components}"

    val navigation_runtime = "androidx.navigation:navigation-runtime:${Versions.nav_components}"

    val navigation_dynamic = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_components}"
    val material_dialogs = "com.afollestad.material-dialogs:core:${Versions.material_dialogs}"
    val material_dialogs_input = "com.afollestad.material-dialogs:input:${Versions.material_dialogs}"
    val play_core = "com.google.android.play:core:${Versions.play_core}"
    val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"
    val firebase_firestore = "com.google.firebase:firebase-firestore-ktx:${Versions.firebase_firestore}"
    val firebase_auth = "com.google.firebase:firebase-auth:${Versions.firebase_auth}"
    val firebase_analytics = "com.google.firebase:firebase-analytics:${Versions.firebase_analytics}"
    val firebase_crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.firebase_crashlytics}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2_version}"
    val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2_version}"

    val markdown_processor = "com.yydcdut:markdown-processor:${Versions.markdown_processor}"

    val recycler_view = "androidx.recyclerview:recyclerview:${Versions.recycle_view_version}"
    val glides = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    val glide_github = "com.github.bumptech.glide:compiler:${Versions.glide_version}"

    //Hilt Component
    val hilt_viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${hilt_lifecycle_viewmodel}"
    val hilt_viewModel_kapt = "androidx.hilt:hilt-compiler:$hilt_lifecycle_viewmodel"
    val hilt_dagger = "com.google.dagger:hilt-android:${Versions.hilt_dagger_version}"

    val colorGenerator = "com.amulyakhare:com.amulyakhare.textdrawable:${Versions.colorGeneratorVetsion}"

    // Lifecycle components
    val lifecycle_common = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}"
    val lifecycle_liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_version}"
    val life_cycle_extension = "androidx.lifecycle:lifecycle-extensions:${Versions.life_cycle_extension_version}"

    // Room components
    val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    //Coroutines Component
    val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"

    // Preferences DataStore
    val sharePrefence = "androidx.datastore:datastore-preferences:${Versions.preference}"


    //material dialog in android
    var  materialDialog = "com.afollestad.material-dialogs:core:${Versions.matieral_dialogs_version}"

    //gogole current location serveice
    val googlelocationservice = "com.google.android.gms:play-services-location:${Versions.locationservice}"


}