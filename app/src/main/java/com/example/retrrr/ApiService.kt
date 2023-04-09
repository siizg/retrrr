package com.example.retrrr

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body user : User) : Response <User>

    @POST("logIn")
    suspend fun logInUser(@Body user : User) : Response <User>
}