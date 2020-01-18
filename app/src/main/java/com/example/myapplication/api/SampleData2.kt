package com.example.myapplication.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SampleData2(

    val field4: Int,

    val field5: String,

    val field6: Double
)