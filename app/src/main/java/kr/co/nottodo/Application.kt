package kr.co.nottodo

import android.app.Application
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}
