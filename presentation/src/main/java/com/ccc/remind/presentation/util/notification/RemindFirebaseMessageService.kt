package com.ccc.remind.presentation.util.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ccc.remind.R
import com.ccc.remind.domain.usecase.notification.PostNotificationUseCase
import com.ccc.remind.presentation.ui.main.MainActivity
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TARGET_ID
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TYPE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/* TODO: Notification
- 감정 묻기
*/

@AndroidEntryPoint
class RemindFirebaseMessageService : FirebaseMessagingService() {
    @Inject
    lateinit var postNotificationsUseCase: PostNotificationUseCase

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    // 데이터 메시지 !!
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // 알림 메세지가 데이터 형태로 들어옴

        // 채널을 만듬 ( 내가 만든 메소드 )
        // 8.0 이상일 경우에만 채널이 생성됨( 코드상 )
        createNotificationChannel()

        val title = message.notification?.title
        val text = message.notification?.body
        val type: String? = message.data["type"]
        val targetId: String? = message.data["targetId"]

        CoroutineScope(Dispatchers.IO).launch {
            postNotificationsUseCase(title, text, type, targetId)
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this)
            .notify(1, createNotification(title, text, type, targetId))
    }


    private fun createNotificationChannel() {

        // 만약 현재 버전이 Oreo( 8버전 )버전 보다 크다면 채널을 만듬
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)

        }
    }

    private fun createNotification(
        title: String?,
        text: String?,
        type: String?,
        targetId: String?
    ): Notification {
        val requestCode = Random.nextInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(NOTIFICATION_INTENT_EXTRA_TYPE, type)
            putExtra(NOTIFICATION_INTENT_EXTRA_TARGET_ID, targetId)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_r_small)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        // 여기서 setAutoCancel(true)은 알림 클릭시에 알림이 실행되면서 알림이 자동으로 사라지게 하는 설정이다.( 이걸 안하면 알림을 클릭해도 사라지지 않고 남아있는다 )

        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_NAME = "친구 알림"
        private const val CHANNEL_DESCRIPTION = "친구 이벤트 알림"
        private const val CHANNEL_ID = "Friend Event Notification"
    }

}