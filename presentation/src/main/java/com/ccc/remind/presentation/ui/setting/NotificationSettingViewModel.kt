package com.ccc.remind.presentation.ui.setting

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.setting.GetNotificationSettingUseCase
import com.ccc.remind.domain.usecase.setting.UpdateNotificationSettingUseCase
import com.ccc.remind.presentation.MyApplication
import com.ccc.remind.presentation.base.ComposeLifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingViewModel @Inject constructor(
    private val getNotificationSetting: GetNotificationSettingUseCase,
    private val updateNotificationSetting: UpdateNotificationSettingUseCase
) : ComposeLifecycleViewModel() {
    private val _isOnNotification = MutableStateFlow(false)
    val isOnNotification = _isOnNotification.asStateFlow()

    init {
        initNotificationSetting()
    }

    private fun initNotificationSetting() {
        addOn(Lifecycle.Event.ON_START) {
            viewModelScope.launch {
                getNotificationSetting().collect {
                    _isOnNotification.value = it && areNotificationsEnabled(MyApplication.applicationContext())
                }
            }
        }
    }

    fun toggleNotification(enable: Boolean) {
        viewModelScope.launch {
            updateNotificationSetting(enable)
            _isOnNotification.value = enable
        }
    }

    fun isRuntimePermissionRequired(): Boolean {
        // Android 13 이상에서 런타임 권한 필요 여부 확인
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    fun areNotificationsEnabled(context: Context): Boolean {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }

    fun openAppSettings(context: Context) {
        val intent = Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
    }


    companion object {
        private const val TAG = "NotificationSettingViewModel"
    }
}