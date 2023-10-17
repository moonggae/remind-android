package com.ccc.remind.presentation.ui.component.model

import com.ccc.remind.R
import com.ccc.remind.presentation.MyApplication


private val getString: (id: Int) -> String = MyApplication.applicationContext().resources::getString

enum class MindFilter(
    override val text: String
) : IToggleValue {
    ALL(getString(R.string.mind_filter_all)),
    POSITIVE(getString(R.string.mind_filter_positive)),
    NEGATIVE(getString(R.string.mind_filter_negative)),
    NORMAL(getString(R.string.mind_filter_normal))
}