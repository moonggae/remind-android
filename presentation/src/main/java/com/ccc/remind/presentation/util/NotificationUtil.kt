package com.ccc.remind.presentation.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

/* TODO
- ask notification permission one time when user is logged in
- add new setting to turn on/off notification
    -> when user turn on the notification and permission is not allowed, ask to user notification permission
*/
class NotificationUtil(
    private val activity: ComponentActivity
) {
    constructor(activity: AppCompatActivity) : this(activity as ComponentActivity)

    companion object {
        fun getFCMToken(onSuccess: (token: String) -> Unit) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if(!task.isSuccessful) {
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
        askNotificationPermission {
            // TODO
        }
    }

    private fun askNotificationPermission(onPermitted: () -> Unit) {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
                onPermitted()
            } else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}