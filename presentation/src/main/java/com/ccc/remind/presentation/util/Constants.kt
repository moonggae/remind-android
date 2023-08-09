package com.ccc.remind.presentation.util

import com.ccc.remind.BuildConfig
import com.ccc.remind.presentation.ui.navigation.Route

object Constants {
    val START_TOP_SCREEN =
        if(BuildConfig.DEBUG) Route.Main.Home
        else Route.Main.Home

    val BASE_URL = "http://10.0.2.2:3000"
}