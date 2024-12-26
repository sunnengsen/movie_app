package com.example.movieapp.data.model

data class HomeData(
    val slides: List<SlideModel>,
    val latestMovies: List<MovieModel>,
    val randomMovies: List<MovieModel>
)

data class SlideModel(
    val id: Int,
    val slideTitle: String,
    val slideUrl: String
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