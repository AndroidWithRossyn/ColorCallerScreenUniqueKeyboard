buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath ("org.jetbrains.kotlin:kotlin-annotation-processing-gradle:1.5.10")
    }
}

plugins {
    id("org.jetbrains.kotlin.android") version "1.7.21" apply false
    base
}

subprojects {
    repositories {
        mavenCentral()
        google()
        jcenter()
        maven { url = uri("https://www.jitpack.io" ) }
        maven { url = uri("https://plugins.gradle.org/m2/")}
    }


}