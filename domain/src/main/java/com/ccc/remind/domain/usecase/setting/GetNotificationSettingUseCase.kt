package com.ccc.remind.domain.usecase.setting

import com.ccc.remind.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke(): Flow<Boolean> = settingRepository.getNotificationSetting()
}