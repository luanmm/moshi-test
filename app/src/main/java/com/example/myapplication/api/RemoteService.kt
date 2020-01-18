package com.example.myapplication.api

import retrofit2.http.GET

interface RemoteService {

    @GET("/test")
    suspend fun <T> test(): Result<T>
}