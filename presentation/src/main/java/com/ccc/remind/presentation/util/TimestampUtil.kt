package com.ccc.remind.presentation.util

import com.ccc.remind.R
import com.ccc.remind.presentation.MyApplication
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun getTimestamp(createDateTime: Long): String {
    val getString:(id: Int) -> String = MyApplication.applicationContext().resources::getString
    val nowDateTime = Calendar.getInstance().timeInMillis
    val differenceMillis = nowDateTime - createDateTime

    val timeUnits = arrayOf(
        TimeUnit.DAYS.toMillis(365) to R.string.timestamp_years_ago,
        TimeUnit.DAYS.toMillis(30) to R.string.timestamp_months_ago,
        TimeUnit.DAYS.toMillis(7) to R.string.timestamp_weeks_ago,
        TimeUnit.DAYS.toMillis(1) to R.string.timestamp_days_ago,
        TimeUnit.HOURS.toMillis(1) to R.string.timestamp_hours_ago,
        TimeUnit.MINUTES.toMillis(1) to R.string.timestamp_minutes_ago
    )

    for ((unitMillis, stringId) in timeUnits) {
        val value = differenceMillis / unitMillis
        if (value > 0) return "$value${getString(stringId)}"
    }

    return getString(R.string.timestamp_just_before)
}

