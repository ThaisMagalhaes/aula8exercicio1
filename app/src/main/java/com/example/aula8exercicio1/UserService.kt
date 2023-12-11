package com.example.aula8exercicio1

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/users/jpescola")
    fun getUser(): Call<UserResponse>
}