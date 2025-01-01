package com.example.movieapp.data.model

data class ActorNDirectorData(
    val actors: List<Actor>,
    val directors: List<Director>
)

data class Director(
    val id: Int,
    val name: String,
    val birthDate: String,
    val profileUrl: String,
    val biography: String
)

data class Actor(
    val id: Int,
    val name: String,
    val birthDate: String,
    val profileUrl: String,
    val biography: String
)


