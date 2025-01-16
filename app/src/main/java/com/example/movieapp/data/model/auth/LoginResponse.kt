package com.example.movieapp.data.model.auth

data class LoginResponse(
    val token: String,
    val role: String,
    val userId: Int
)