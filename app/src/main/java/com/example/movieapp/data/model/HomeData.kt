package com.example.movieapp.data.model

data class HomeData(
    val slides: List<MovieModel>,
    val latestMovies: List<MovieModel>,
    val randomMovies: List<MovieModel>,
    val topMovies: List<MovieModel>,
)
