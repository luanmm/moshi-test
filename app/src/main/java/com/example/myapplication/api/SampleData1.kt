package com.example.myapplication.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SampleData1(

    val field1: Int,

    val field2: String,

    val field3: Double
)