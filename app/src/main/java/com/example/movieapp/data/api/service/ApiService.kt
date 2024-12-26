package com.example.movieapp.data.api.service

import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.HomeData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieData
import com.example.movieapp.data.model.auth.LoginRequest
import com.example.movieapp.data.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body response: LoginRequest): ApiResponse<LoginResponse>

    @GET("home")
    suspend fun loadHomeData(): ApiResponse<HomeData>

    @GET("movie/group")
    suspend fun loadMovieData(): ApiResponse<MovieData>

    @GET("movie/type/Drama")
    suspend fun loadMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Drama")
    suspend fun loadDramaMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Action")
    suspend fun loadActionMovies(): ApiResponse<List<Movie>>


}