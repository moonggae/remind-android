package com.ccc.remind.data.util

import androidx.room.TypeConverter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class LocalDataTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromZonedDateTime(value: ZonedDateTime): Long {
            return value.toInstant().toEpochMilli()
        }

        @TypeConverter
        @JvmStatic
        fun toZonedDateTime(value: Long): ZonedDateTime {
            val instant = Instant.ofEpochMilli(value)
            return ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"))
        }
    }
}