package com.ccc.remind.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ccc.remind.presentation.di.network.InterceptorOkHttpClient
import com.ccc.remind.presentation.ui.App
import com.ccc.remind.presentation.util.initCoil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @InterceptorOkHttpClient
    @Inject lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCoil(
            context = this,
            okHttpClient = okHttpClient
        )
        setContent {
            App()
        }
    }
}

