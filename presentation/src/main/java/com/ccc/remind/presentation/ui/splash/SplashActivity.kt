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
            return@addOnPreDrawListener viewModel.isInitialized.value
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isInitialized.collect {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//                        val loggedInUser = viewModel.loggedInUser.value
//                        if(loggedInUser == null) {
//                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//                        } else if(loggedInUser.displayName == null) {
//                            startActivity(Intent(this@SplashActivity, DisplayNameActivity::class.java))
//                        } else {
//                            // todo : go to 메인화면
//                        }
                    }
                }
            }
        }
    }
}