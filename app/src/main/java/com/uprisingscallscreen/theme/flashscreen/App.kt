package com.uprisingscallscreen.theme.flashscreen

import android.app.Application

import com.uprisingscallscreen.theme.flashscreen.koin.getApplicationModules
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import keyboard.neon.newboard.KeyboardInitClass
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


@Suppress("unused")
class App : Application() {

    private var instance: App? = null
    @Synchronized
    fun getInstance(): App? {
        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this



        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true



        MobileAds.initialize(this) { initializationStatus -> }
        KeyboardInitClass(this).onCreate()


        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(
                getApplicationModules()
            )
        }
    }



}