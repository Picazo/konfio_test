package com.konfiotest

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*

class KonfioTestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin()
        injectFeature()
        Locale.setDefault(Locale.US)
        val config = Configuration()
        config.setLocale(Locale.US)
        baseContext.createConfigurationContext(config)
    }

    private fun initKoin(){
        startKoin {
            if (BuildConfig.DEBUG){
                androidLogger()
            }
            androidContext(this@KonfioTestApplication)
        }
    }

    companion object {
        lateinit var instance: KonfioTestApplication
            private set
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}