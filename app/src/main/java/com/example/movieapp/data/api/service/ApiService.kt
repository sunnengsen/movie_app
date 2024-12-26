package com.example.movieapp.data.api.service

import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.HomeData
import com.example.movieapp.data.model.auth.LoginRequest
import com.example.movieapp.data.model.auth.LoginResponse
import com.example.movieapp.models.Movie
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body response: LoginRequest): ApiResponse<LoginResponse>
//    home data
    @GET("movie")
    suspend fun loadHomeData(): ApiResponse<HomeData>
    @GET("/movie")
    suspend fun getMovies(): List<Movie>
}