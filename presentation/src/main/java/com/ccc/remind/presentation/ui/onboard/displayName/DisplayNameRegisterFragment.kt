package com.ccc.remind.presentation.ui.onboard.displayName

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ccc.remind.R
import com.ccc.remind.databinding.FragmentDisplayNameRegisterBinding
import com.ccc.remind.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisplayNameRegisterFragment : BaseFragment<FragmentDisplayNameRegisterBinding, DisplayNameViewModel>() {
    override val viewModel: DisplayNameViewModel by activityViewModels()
    override val layoutResID: Int = R.layout.fragment_display_name_register

    override fun initVariable() {}

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
                    if (it.isValidDisplayName) {
                        binding.saveButton.isEnabled = true
                        binding.displayNameRuleTextview.setTextColor(Color.parseColor("#595959"))
                    } else {
                        binding.saveButton.isEnabled = false
                        if (it.hasEditedDisplayName) {
                            binding.displayNameRuleTextview.setTextColor(ContextCompat.getColor(baseActivity, R.color.red_700))
                        }
                    }
                }
            }
        }
    }

    override fun initView() {}

}