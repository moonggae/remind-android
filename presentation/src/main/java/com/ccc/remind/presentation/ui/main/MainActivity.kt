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
import com.ccc.remind.presentation.navigation.moveToNotificationRoute
import com.ccc.remind.presentation.ui.App
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.onboard.login.LoginActivity
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TARGET_ID
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TYPE
import com.ccc.remind.presentation.util.initCoil
import com.ccc.remind.presentation.util.notification.NotificationUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
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
    private val firebaseAnalytics = Firebase.analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            NotificationUtil(this@MainActivity).askNotificationPermission()
        }
        observeLoginState()
        initCoil(
            context = this,
            okHttpClient = okHttpClient
        )

        setContent {
            navController = rememberNavController()
            navController.addOnDestinationChangedListener { _, destination, _ ->
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                    param(FirebaseAnalytics.Param.SCREEN_NAME, destination.route ?: "unknown")
                }
            }

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
                    if(uiState.isInitialized && uiState.currentUser == null) {
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
        lifecycleScope.launch {// todo : 앱 완전 종료 상태에서 실행 안됨
//            Log.d("TAG", "MainActivity - onNewIntent - MyApplication.lifecycleEvent: ${MyApplication.lifecycleEvent}")
            moveToNotificationRoute(
                navController = navController,
                notificationType = intent?.getStringExtra(NOTIFICATION_INTENT_EXTRA_TYPE),
                notificationTargetId = intent?.getStringExtra(NOTIFICATION_INTENT_EXTRA_TARGET_ID)
            )
        }
    }
}