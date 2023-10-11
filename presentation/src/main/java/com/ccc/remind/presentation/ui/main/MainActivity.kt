package com.ccc.remind.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.presentation.di.network.InterceptorOkHttpClient
import com.ccc.remind.presentation.ui.App
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.onboard.login.LoginActivity
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TARGET_ID
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TYPE
import com.ccc.remind.presentation.util.initCoil
import com.ccc.remind.presentation.util.notification.NotificationUtil
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
    private lateinit var navController: NavController

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
            navController = rememberNavController()

            App(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        lifecycleScope.launch {
            mainViewModel.moveToNotificationRoute(
                navController = navController,
                notificationType = intent?.getStringExtra(NOTIFICATION_INTENT_EXTRA_TYPE),
                notificationTargetId = intent?.getStringExtra(NOTIFICATION_INTENT_EXTRA_TARGET_ID)
            )
        }
    }
}