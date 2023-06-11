package com.ccc.remind.presentation.ui.login

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ccc.remind.R
import com.ccc.remind.databinding.ActivityLoginBinding
import com.ccc.remind.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val viewModel by viewModels<LoginViewModel>()
    override val layoutResID: Int = R.layout.activity_login

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun initVariable() {
        binding.viewModel = viewModel
    }

    override fun initListener() {
        binding.kakaoLoginButton.setOnClickListener {
            Log.d(TAG, "LoginActivity - kakaoLoginButton")
        }
    }

    override fun initObserver() {}

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        binding.slideIndicator.apply {
            createIndicators(4, 0)
            setViewPager(binding.slideViewpager)
        }

        binding.slideViewpager.apply {
            adapter = SlideViewPagerAdapter(
                arrayListOf(
                    R.drawable.login_slide_0,
                    R.drawable.login_slide_0,
                    R.drawable.login_slide_0,
                    R.drawable.login_slide_0
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