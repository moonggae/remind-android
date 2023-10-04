package com.ccc.remind.presentation.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/* TODO
- ask notification permission one time when user is logged in
- add new setting to turn on/off notification
    -> when user turn on the notification and permission is not allowed, ask to user notification permission
    -> add foreground notification setting
*/
class NotificationUtil(
    private val activity: ComponentActivity
) {
    constructor(activity: AppCompatActivity) : this(activity as ComponentActivity)

    private val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

    companion object {
        fun getFCMToken(onSuccess: (token: String) -> Unit) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.w(task.exception)
                }

                val token = task.result
                onSuccess(token)
            }
        }
    }

    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 권한 허용시
        } else {
            // 권한 거부시
        }
    }

    fun call(text: String) {

    }

    // TODO : Android 13 이상에서는 런타임에서 알람 권한을 요청하고 이하에서는 알람 설정 창으로 이동시킴
    fun askNotificationPermission(): Flow<Boolean> = flow {
        if (ContextCompat.checkSelfPermission(activity, notificationPermission) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            emit(true)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(notificationPermission)
            } else {
                // TODO: 설정 창에서 권한 요청
            }
        }
    }
}