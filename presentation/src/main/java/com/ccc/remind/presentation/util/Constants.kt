package com.ccc.remind.presentation.util

import com.ccc.remind.BuildConfig
import com.ccc.remind.presentation.ui.navigation.Route

object Constants {
    val START_TOP_SCREEN =
        if(BuildConfig.DEBUG) Route.Main.Home
        else Route.Main.Home

    const val BASE_URL = "https://egchoi.dev"

    const val POST_MIND_RESULT_KEY = "_POST_MIND_RESULT_KEY_"
}