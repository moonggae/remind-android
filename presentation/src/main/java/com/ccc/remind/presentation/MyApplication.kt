package com.ccc.remind.presentation

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
        private var accessToken: String? = null
        fun applicationContext(): Context {
            return instance.applicationContext
        }

        fun getAccessToken(): String? = accessToken

        fun setAccessToken(token: String) {
            accessToken = token
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}