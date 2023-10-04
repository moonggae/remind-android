package com.ccc.remind.presentation.util

import com.ccc.remind.BuildConfig
import com.ccc.remind.presentation.ui.navigation.Route

object Constants {
    val START_TOP_SCREEN =
        if(BuildConfig.DEBUG) Route.Main.Home
        else Route.Main.Home

//    const val BASE_URL = "https://egchoi.dev"
    const val BASE_URL = "http://10.0.2.2"

    const val INVITE_URL_PATH = "app/android"

    const val INVITE_CODE_LENGTH = 6

    const val POST_MIND_RESULT_KEY = "_POST_MIND_RESULT_KEY_"

    const val REQUEST_CODE_NOTIFICATION = 1000
}