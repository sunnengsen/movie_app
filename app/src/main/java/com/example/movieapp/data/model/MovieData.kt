package com.example.movieapp.data.model

data class MovieData(
    val status: String?,
    val message: String?,
    val data: List<MovieModel>?
)

data class MovieModel(
    val id: Int,
    val title: String,
    val description: String?,
    val releaseDate: String?,
    val posterUrl: String,
    val rating: String?,
    val movieType: MovieType
)

data class MovieType(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String?
)