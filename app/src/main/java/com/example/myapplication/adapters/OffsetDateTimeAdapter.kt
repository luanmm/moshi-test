package com.example.myapplication.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class OffsetDateTimeAdapter {

    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

    @ToJson
    fun toJson(value: OffsetDateTime): String = value.format(DATE_FORMATTER)

    @FromJson
    fun fromJson(value: String): OffsetDateTime = DATE_FORMATTER.parse(value, OffsetDateTime::from)
}