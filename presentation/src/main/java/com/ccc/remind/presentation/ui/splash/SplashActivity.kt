package com.ccc.remind.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ccc.remind.presentation.ui.main.MainActivity
import com.ccc.remind.presentation.ui.onboard.displayName.DisplayNameActivity
import com.ccc.remind.presentation.ui.onboard.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    private val viewModel : SplashViewModel by viewModels()
    
    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener {
            return@addOnPreDrawListener viewModel.uiState.value.isInitialized
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        when(it.loginState) {
                            LoginState.EMPTY ->
                                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            LoginState.LOGGED_IN_NO_DISPLAY_NAME ->
                                startActivity(Intent(this@SplashActivity, DisplayNameActivity::class.java))
                            LoginState.LOGGED_IN ->
                                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        }
                    }
                }
            }
        }
    }
}