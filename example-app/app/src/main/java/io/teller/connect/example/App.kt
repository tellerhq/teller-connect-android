package io.teller.connect.example

import android.app.Application
import timber.log.Timber.*
import timber.log.Timber.Forest.plant

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
    }
}