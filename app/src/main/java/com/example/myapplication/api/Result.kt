package com.example.myapplication.api

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Result<T> (

    var data: T? = null,

    var timestamp: OffsetDateTime? = null
)
