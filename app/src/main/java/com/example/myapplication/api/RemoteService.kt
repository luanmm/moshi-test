package com.example.myapplication.api

import retrofit2.http.GET

interface RemoteService {

    @GET("api.json")
    suspend fun <T> test(): Result<T>
}