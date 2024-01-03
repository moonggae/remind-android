package com.ccc.remind.presentation.util.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import com.ccc.remind.R
import com.ccc.remind.domain.usecase.notification.PostNotificationUseCase
import com.ccc.remind.domain.usecase.setting.GetNotificationSettingUseCase
import com.ccc.remind.presentation.MyApplication
import com.ccc.remind.presentation.ui.main.MainActivity
import com.ccc.remind.presentation.util.Constants
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TARGET_ID
import com.ccc.remind.presentation.util.Constants.NOTIFICATION_INTENT_EXTRA_TYPE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class RemindFirebaseMessageService : FirebaseMessagingService() {
    @Inject
    lateinit var postNotificationsUseCase: PostNotificationUseCase
    @Inject
    lateinit var getNotificationSettingUseCase: GetNotificationSettingUseCase

    private val atomicId = AtomicInteger(0)

    private val notificationId: Int
        get() = atomicId.incrementAndGet()

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    private val isAppForeground: Boolean
        get() = MyApplication.lifecycleEvent == Lifecycle.Event.ON_RESUME

    // 데이터 메시지 !!
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // 알림 메세지가 데이터 형태로 들어옴

        // 채널을 만듬 ( 내가 만든 메소드 )
        // 8.0 이상일 경우에만 채널이 생성됨( 코드상 )
        createNotificationChannel()

        /* note
        기존에 앱이 background 상태에서 알림을 수신하면 onMessageReceived를 실행하지 않고 알림을 발생시킴
        message.notification -> message.data 로 변경 후
        모든 notification이 onMessageReceived를 실행하여 알림을 발생시킴
        * */
        val title = message.data["title"]
        val text = message.data["body"]
        val type: String? = message.data["type"]
        val targetId: String? = message.data["targetId"]

        CoroutineScope(Dispatchers.IO).launch {
            postNotificationsUseCase(title, text, type, targetId)
        }

        CoroutineScope(Dispatchers.IO).launch {
            getNotificationSettingUseCase().collect { isOn ->
                if(checkNotificationPermission() && isOn) {
                    NotificationManagerCompat.from(this@RemindFirebaseMessageService).apply {
                        notify(notificationId, createNotification(title, text, type, targetId))
                        notify(100, createSummaryNotification())
                    }
                }
            }
        }
    }

    private fun checkNotificationPermission(): Boolean {
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }


    private fun createNotificationChannel() {

        // 만약 현재 버전이 Oreo( 8버전 )버전 보다 크다면 채널을 만듬
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)

        }
    }

    private fun createSummaryNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_r_small)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        // 앱이 foreground 상태에서만 heads-up 알림 실행
        .setSilent(this.isAppForeground)
        .setGroup(Constants.NOTIFICATION_COMMON_GROUP_KEY)
        .setGroupSummary(true)
        .build()

    private fun createNotification(
        title: String?,
        text: String?,
        type: String?,
        targetId: String?
    ): Notification {
//        Log.d("TAG", "RemindFirebaseMessageService - createNotification - MyApplication.lifecycleEvent: ${MyApplication.lifecycleEvent}")
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
            .setSilent(this.isAppForeground)
            .setGroup(Constants.NOTIFICATION_COMMON_GROUP_KEY)


        // 여기서 setAutoCancel(true)은 알림 클릭시에 알림이 실행되면서 알림이 자동으로 사라지게 하는 설정이다.( 이걸 안하면 알림을 클릭해도 사라지지 않고 남아있는다 )

        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_NAME = "Friend Event"
        private const val CHANNEL_DESCRIPTION = "Friend Event Notification"
        private const val CHANNEL_ID = "Friend Event"
    }
}