package com.example.movieapp.data.api.service

import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.HomeData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieData
import com.example.movieapp.data.model.ProfileData
import com.example.movieapp.data.model.auth.LoginRequest
import com.example.movieapp.data.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body response: LoginRequest): ApiResponse<LoginResponse>

    @GET("home")
    suspend fun loadHomeData(): ApiResponse<HomeData>

    @GET("movie/group")
    suspend fun loadMovieData(): ApiResponse<MovieData>

    @GET("movie/type/Drama")
    suspend fun loadMovies(): ApiResponse<List<Movie>>

    @GET("movie")
    suspend fun loadAllMovie(): ApiResponse<List<Movie>>

    @GET("movie/type/Action")
    suspend fun loadActionMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Comedy")
    suspend fun loadComedyMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Thriller")
    suspend fun loadThrillerMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Fantasy")
    suspend fun loadFantasyMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Horror")
    suspend fun loadHorrorMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Musical")
    suspend fun loadMusicalMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Romance")
    suspend fun loadRomanceMovies(): ApiResponse<List<Movie>>

    @GET("movie/type/Adventure")
    suspend fun loadAdventureMovies(): ApiResponse<List<Movie>>

    @GET("profile")
    suspend fun loadProfile(@Header("Authorization") token: String): ApiResponse<ProfileData>

}