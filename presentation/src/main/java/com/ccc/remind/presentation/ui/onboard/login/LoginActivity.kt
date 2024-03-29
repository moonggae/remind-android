package com.ccc.remind.presentation.ui.onboard.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ccc.remind.BuildConfig
import com.ccc.remind.R
import com.ccc.remind.databinding.ActivityLoginBinding
import com.ccc.remind.presentation.MyApplication
import com.ccc.remind.presentation.base.BaseActivity
import com.ccc.remind.presentation.ui.main.MainActivity
import com.ccc.remind.presentation.ui.onboard.displayName.DisplayNameActivity
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val viewModel by viewModels<LoginViewModel>()
    override val layoutResID: Int = R.layout.activity_login

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun initVariable() {
        binding.viewModel = viewModel
        KakaoSdk.init(MyApplication.applicationContext(), BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    override fun initListener() {
        binding.kakaoLoginButton.setOnClickListener {
            viewModel.kakaoLogin(this)
        }
        if(BuildConfig.DEBUG) {
            binding.test1LoginButton.setOnClickListener {
                viewModel.testLogin("1")
            }
            binding.test2LoginButton.setOnClickListener {
                viewModel.testLogin("2")
            }
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.isLoggedIn.collect {
                if(!it) return@collect

                if(viewModel.displayName.value == null) {
                    startActivity(Intent(this@LoginActivity, DisplayNameActivity::class.java))
                } else {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }

                finish()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        binding.slideIndicator.apply {
            createIndicators(1, 0)
            setViewPager(binding.slideViewpager)
        }

        binding.slideViewpager.apply {
            adapter = SlideViewPagerAdapter(
                arrayListOf(
                    R.drawable.login_slide_0,
//                    R.drawable.login_slide_0,
//                    R.drawable.login_slide_0,
//                    R.drawable.login_slide_0
                )
            )
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.slideIndicator.animatePageSelected(position)
                }
            })
        }
    }
}