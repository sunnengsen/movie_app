package com.example.movieapp.data.model

data class ProfileData(
    val userId: Int,
    val username: String,
    val email: String,
    val password: String,
    val role: String,
    val profileUrl: String?
)