package com.ccc.remind.domain.usecase.setting

import com.ccc.remind.domain.repository.SettingRepository
import javax.inject.Inject

class UpdateNotificationSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(enable: Boolean) = settingRepository.updateNotificationSetting(enable)
}