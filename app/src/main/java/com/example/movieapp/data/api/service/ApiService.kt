package com.example.movieapp.data.api.service

import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.data.model.SlideModel
import com.example.movieapp.data.model.auth.LoginRequest
import com.example.movieapp.data.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("slide/1")
    suspend fun getSlide(): ApiResponse<SlideModel>
    @GET("movie")
    suspend fun getMovie(): ApiResponse<List<MovieModel>>
    @POST("login")
    suspend fun login(@Body response: LoginRequest): ApiResponse<LoginResponse>




}