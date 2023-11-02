package com.ccc.remind.data.util

import androidx.room.TypeConverter
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LocalDataTypeConverter : JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {
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
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        }
    }

    override fun serialize(src: ZonedDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ZonedDateTime {
        return ZonedDateTime
            .parse(json?.asString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .withZoneSameInstant(ZoneId.systemDefault())
    }
}