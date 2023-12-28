package com.ccc.remind.domain.usecase.setting

import com.ccc.remind.domain.entity.setting.HistoryViewType
import com.ccc.remind.domain.repository.SettingRepository
import javax.inject.Inject

class UpdateHistoryViewTypeUseCase @Inject constructor(
    private val settingRepository: SettingRepository
){
    suspend operator fun invoke(type: HistoryViewType) = settingRepository.updateHistoryViewType(type)
}