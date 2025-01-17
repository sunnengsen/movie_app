package com.example.movieapp.data.model.auth

import com.example.movieapp.data.model.ProfileData


data class ProfileResponseData(
    val user: ProfileData,
    val token: String
)