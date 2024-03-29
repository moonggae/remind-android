package com.ccc.remind.presentation.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ccc.remind.presentation.ui.main.MainActivity
import com.ccc.remind.presentation.ui.onboard.displayName.DisplayNameActivity
import com.ccc.remind.presentation.ui.onboard.login.LoginActivity
import com.ccc.remind.presentation.util.NetworkManager
import com.ccc.remind.presentation.util.notification.NotificationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel : SplashViewModel by viewModels()
    private var networkEnabled: Boolean = false

    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        Log.d(TAG, "SplashActivity - handleIntent - appLinkData: ${appLinkData}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)

        networkEnabled = NetworkManager.networkEnabled(this)

        if (!networkEnabled) {
            Toast.makeText(this, "인터넷 연결 상태를 확인 해주세요.",  Toast.LENGTH_LONG).show()
        }

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener {
            return@addOnPreDrawListener viewModel.uiState.value.isInitialized && networkEnabled
        }

        observeDataInit()
    }

    private fun observeDataInit() {
        if(!networkEnabled) {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
            return
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        if(it.doneUserInit && it.successFCMTokenInit == null) {
                            updateFCMToken()
                        }

                        if(it.isInitialized) {
                            when(it.loginState) {
                                LoginState.EMPTY, LoginState.REFRESH_TOKEN_EXPIRED ->
                                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                                LoginState.LOGGED_IN_NO_DISPLAY_NAME ->
                                    startActivity(Intent(this@SplashActivity, DisplayNameActivity::class.java))
                                LoginState.LOGGED_IN ->
                                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            }
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun updateFCMToken() {
        NotificationUtil.getFCMToken { token ->
            lifecycleScope.launch {
                viewModel.refreshUserFCMToken(token)    
            }
        }
    }
}