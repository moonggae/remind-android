package com.ccc.remind.presentation

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application(), LifecycleEventObserver {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }

        var lifecycleEvent = Lifecycle.Event.ON_DESTROY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        lifecycleEvent = event
    }
}