package com.ccc.remind.presentation.ui.onboard.displayName

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ccc.remind.R
import com.ccc.remind.databinding.ActivityDisplayNameBinding
import com.ccc.remind.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisplayNameActivity :
    BaseActivity<ActivityDisplayNameBinding, DisplayNameViewModel>() {
    override val viewModel by viewModels<DisplayNameViewModel>()
    override val layoutResID: Int = R.layout.activity_display_name

    private lateinit var navController : NavController

    companion object {
        private const val TAG = "DisplayNameRegisterActivity"
    }

    override fun initVariable() {
//        navController = Navigation.findNavController(binding.displayNameFragmentContainer)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.display_name_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun initListener() {

    }

    override fun initObserver() {

    }

    override fun initView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    Log.d(TAG, "DisplayNameActivity - initView - it.isRegisteredDisplayName: ${it.isRegisteredDisplayName}")
                    if (it.isRegisteredDisplayName) {
                        navController.navigate(R.id.action_displayNameRegisterFragment_to_displayNameShowFragment)
                    }
                }
            }
        }
    }


}