package com.ccc.remind.domain.usecase.setting

import com.ccc.remind.domain.repository.SettingRepository
import javax.inject.Inject

class UpdateDenyNotificationPermission @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(deny: Boolean) = settingRepository.updateUserDenyNotificationPermission(deny)
}