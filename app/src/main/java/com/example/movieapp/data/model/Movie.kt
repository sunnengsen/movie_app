package com.example.movieapp.data.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String?,
    val releaseDate: String?,
    val posterUrl: String,
    val rating: String?,
    val movieType: MovieType?,
    val movieUrl: String?
)
