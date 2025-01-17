package com.example.movieapp.data.model

data class ProfileData(
    val userId: Int?,
    val username: String?,
    val email: String?,
    val password: String?,
    val role: String?,
    val profileUrl: String?,
    val bookmarks: List<Bookmark>?
)

data class Bookmark(
    val id: Int,
    val date: String,
    val movie: MovieModel
)

data class MovieModel(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val posterUrl: String,
    val rating: String,
    val movieUrl: String,
    val actorName: String?,
    val movieType: MovieType,
    val reviews: List<Review>
)

data class MovieType(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String?
)

data class Review(
    val id: Int,
    val review: String,
    val rating: Int,
    val comment: String,
    val reviewDate: String,
    val movieId: Int,
    val userId: Int
)