package com.example.movieapp.data.api.service

import com.example.movieapp.data.model.Actor
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.BookmarkRequest
import com.example.movieapp.data.model.BookmarkResponse
import com.example.movieapp.data.model.Director
import com.example.movieapp.data.model.HomeData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieData
import com.example.movieapp.data.model.ProfileData
import com.example.movieapp.data.model.auth.LoginRequest
import com.example.movieapp.data.model.auth.LoginResponse
import com.example.movieapp.data.model.auth.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body response: LoginRequest): ApiResponse<LoginResponse>

    @POST("auth/signup")
    suspend fun signUp(@Body response: SignUpRequest): ApiResponse<LoginResponse>

    @POST("bookmark")
    suspend fun bookmark(@Body response: BookmarkRequest): ApiResponse<BookmarkResponse>

    @DELETE("bookmark/{id}")
    suspend fun removeBookmark(@Path("id") id: BookmarkRequest): ApiResponse<BookmarkResponse>

    @GET("home")
    suspend fun loadHomeData(): ApiResponse<HomeData>

    @GET("movie/group")
    suspend fun loadMovieData(): ApiResponse<MovieData>

    @GET("movie/type/Drama")
    suspend fun loadMovies(): ApiResponse<List<Movie>>

    @GET("movie/all")
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

    @GET("actor")
    suspend fun loadActors(): ApiResponse<List<Actor>>

    @GET("director")
    suspend fun loadDirectors(): ApiResponse<List<Director>>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): ApiResponse<Movie>

    @GET("movie/search/{title}")
    suspend fun searchMovie(@Path("title") title: String): ApiResponse<List<Movie>>

    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body profileData: ProfileData
    ): ApiResponse<ProfileData>

    @DELETE("bookmark/{id}")
    suspend fun deleteBookmark(
        @Header("Authorization") token: String,
        @Path("id") bookmarkId: Int
    ): ApiResponse<ProfileData>

@GET("bookmark")
suspend fun getBookmarks(@Header("Authorization") token: String): ApiResponse<List<BookmarkResponse>>
}