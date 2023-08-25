plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.firebase.crashlytics") version "2.8.1"
    id("com.google.gms.google-services") version "4.3.10"
}
android {
    signingConfigs {
        create("release") {
            storeFile =
                file("C:\\Users\\Anil\\Downloads\\thirdCopyColorCaller\\ColorCallerNeonKeyboard-master\\colorPhone.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }
    compileSdk = 33
    defaultConfig {
        applicationId = "com.uprisingscallscreen.theme.flashscreen"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        renderscriptTargetApi = 29
        renderscriptSupportModeEnabled = true
//        setProperty("archivesBaseName", "Color Phone: Call Screen Theme $versionName")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("release")


        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    Pair("room.schemaLocation", "$projectDir/schemas"),
                    Pair("room.incremental", "true"),
                    Pair("room.expandProjection", "true")
                )
            }
        }
    }

    buildTypes {
        getByName("debug") {

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }


    }


    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }



    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }


    buildFeatures {
        viewBinding = true
        dataBinding=true
    }

}

dependencies {

    implementation("com.github.bumptech.glide:glide:4.15.0")
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("androidx.work:work-runtime-ktx:2.7.0")
    implementation(platform("com.google.firebase:firebase-bom:29.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation ("com.google.firebase:firebase-analytics:21.3.0")
    implementation ("com.google.firebase:firebase-database:20.2.2")
    implementation ("com.google.firebase:firebase-core:21.1.1")
    implementation ("com.google.firebase:firebase-storage:20.2.0")
    implementation ("com.google.firebase:firebase-config")
    implementation ("com.google.firebase:firebase-messaging")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation(project(mapOf("path" to ":aosp")))
    implementation(project(mapOf("path" to ":AdsSdk")))
    implementation("androidx.room", "room-ktx", "2.5.0-beta02")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.3.70")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    kapt("androidx.room", "room-compiler", "2.5.0-beta02")
    val koinVersion = "3.0.2"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    //color caller dependency
    implementation ("com.makeramen:roundedimageview:2.3.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.koushikdutta.ion:ion:3.0.8")
    implementation ("com.koushikdutta.async:androidasync:3.0.8")
    implementation ("com.makeramen:roundedimageview:2.3.0")
    implementation ("com.romainpiel.shimmer:library:1.4.0")
    implementation ("com.github.yalantis:ucrop:2.2.6")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.squareup.okio:okio:2.10.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("org.jsoup:jsoup:1.14.3")
    implementation ("com.android.volley:volley:1.1.1")
    implementation ("com.google.code.gson:gson:2.8.5")
    implementation ("com.commit451:PhotoView:1.2.4")
    implementation ("com.github.devlight:infinitecycleviewpager:1.0.2")
    implementation ("com.github.judemanutd:autostarter:1.0.6")
    implementation ("com.synnapps:carouselview:0.1.4")
    implementation ("com.daimajia.androidanimations:library:2.4@aar")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.25")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("io.reactivex.rxjava2:rxandroid:2.0.2")
    implementation ("androidx.work:work-runtime-ktx:2.7.1")



}
