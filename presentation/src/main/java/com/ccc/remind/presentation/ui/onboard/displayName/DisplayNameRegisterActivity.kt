package com.ccc.remind.presentation.ui.onboard.displayName

import android.graphics.Color
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ccc.remind.R
import com.ccc.remind.databinding.ActivityDisplayNameRegisterBinding
import com.ccc.remind.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisplayNameRegisterActivity :
    BaseActivity<ActivityDisplayNameRegisterBinding, DisplayNameViewModel>() {
    override val viewModel by viewModels<DisplayNameViewModel>()
    override val layoutResID: Int = R.layout.activity_display_name_register

    companion object {
        private const val TAG = "DisplayNameRegisterActivity"
    }

    override fun initVariable() {

    }

    override fun initListener() {
        binding.displayNameTextInputEditText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.updateDisplayName(text)
        }

        binding.saveButton.setOnClickListener {
            viewModel.registerDisplayName()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.isValid) {
                        binding.saveButton.isEnabled = true
                        binding.displayNameRuleTextview.setTextColor(Color.parseColor("#595959"))
                    } else {
                        binding.saveButton.isEnabled = false
                        if (it.hasChanged) {
                            binding.displayNameRuleTextview.setTextColor(ContextCompat.getColor(this@DisplayNameRegisterActivity, R.color.red_700))
                        }
                    }
                }
            }
        }
    }

    override fun initView() {

    }


}