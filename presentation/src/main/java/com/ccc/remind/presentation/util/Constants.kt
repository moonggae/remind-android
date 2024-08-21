package com.ccc.remind.presentation.util

import com.ccc.remind.BuildConfig
import com.ccc.remind.presentation.navigation.Route

object Constants {
    val START_TOP_SCREEN =
        if(BuildConfig.DEBUG) Route.Main.Home
        else Route.Main.Home

    val BASE_URL = BuildConfig.SERVER_URL
    val SOCKET_URL = BuildConfig.SOCKET_URL

    const val INVITE_URL_PATH = "app/android"

    const val INVITE_CODE_LENGTH = 6

    const val POST_MIND_RESULT_KEY = "_POST_MIND_RESULT_KEY_"

    const val NOTIFICATION_INTENT_EXTRA_TYPE = "_NOTIFICATION_INTENT_EXTRA_TYPE_"
    const val NOTIFICATION_INTENT_EXTRA_TARGET_ID = "_NOTIFICATION_INTENT_EXTRA_ID_"
    const val NOTIFICATION_COMMON_GROUP_KEY = "_NOTIFICATION_COMMON_GROUP_KEY_"

    const val REQUEST_CODE_NOTIFICATION = 1000
}