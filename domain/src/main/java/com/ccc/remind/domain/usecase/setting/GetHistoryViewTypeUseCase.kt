package com.ccc.remind.domain.usecase.setting

import com.ccc.remind.domain.repository.SettingRepository
import javax.inject.Inject

class GetHistoryViewTypeUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke() = settingRepository.getHistoryViewType()
}