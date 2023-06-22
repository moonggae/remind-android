package com.ccc.remind.presentation.ui.onboard.displayName

import androidx.fragment.app.activityViewModels
import com.ccc.remind.R
import com.ccc.remind.databinding.FragmentDisplayNameShowBinding
import com.ccc.remind.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DisplayNameShowFragment : BaseFragment<FragmentDisplayNameShowBinding, DisplayNameViewModel>() {
    override val viewModel: DisplayNameViewModel by activityViewModels()
    override val layoutResID: Int = R.layout.fragment_display_name_show

    override fun initVariable() {
        binding.viewModel = viewModel
    }

    override fun initListener() {}

    override fun initObserver() {}

    override fun initView() {}

}