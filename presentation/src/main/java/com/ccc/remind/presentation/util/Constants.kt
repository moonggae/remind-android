package com.ccc.remind.presentation.util

import com.ccc.remind.BuildConfig
import com.ccc.remind.presentation.ui.navigation.Route

object Constants {
    val START_TOP_SCREEN =
        if(BuildConfig.DEBUG) Route.MindPost.Edit
        else Route.Main.Home
}