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
