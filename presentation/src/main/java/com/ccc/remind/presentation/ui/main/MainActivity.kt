package com.ccc.remind.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ccc.remind.presentation.di.network.InterceptorOkHttpClient
import com.ccc.remind.presentation.ui.App
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.onboard.login.LoginActivity
import com.ccc.remind.presentation.util.NotificationUtil
import com.ccc.remind.presentation.util.initCoil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @InterceptorOkHttpClient
    @Inject lateinit var okHttpClient: OkHttpClient

    val sharedViewModel: SharedViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            NotificationUtil(this@MainActivity).askNotificationPermission()
        }
        observeLoginState()
        mainViewModel.checkNotificationPermission()
        initCoil(
            context = this,
            okHttpClient = okHttpClient
        )



        setContent {
            App(sharedViewModel)
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.uiState.collect { uiState ->
                    if(uiState.isInitialized && uiState.user == null) {
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun initNotificationPermission() {
        lifecycleScope.launch {
            mainViewModel.uiState.collect { uiState ->
                if(uiState.isDenyNotification == false) {
                    NotificationUtil(this@MainActivity).askNotificationPermission()
                }
            }
        }
    }
}


