package com.example.movieapp.data.model.auth

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String
)
