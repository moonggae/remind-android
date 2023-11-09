package com.ccc.remind.presentation.ui.component.model

import com.ccc.remind.R
import com.ccc.remind.presentation.MyApplication


private val getString: (id: Int) -> String = MyApplication.applicationContext().resources::getString

enum class MindFilter(
    override val text: String,
    override val value: Int? = null
) : IToggleValue {
    ALL(getString(R.string.mind_filter_all)),
    ENERGY_LOW(getString(R.string.mind_filter_energy),-1),
    ENERGY_HIGH(getString(R.string.mind_filter_energy), 1),
    PLEASANTNESS_LOW(getString(R.string.mind_filter_pleasantness), -1),
    PLEASANTNESS_HIGH(getString(R.string.mind_filter_pleasantness), 1),
//    POSITIVE(getString(R.string.mind_filter_positive)),
//    NEGATIVE(getString(R.string.mind_filter_negative)),
//    NORMAL(getString(R.string.mind_filter_normal))
}