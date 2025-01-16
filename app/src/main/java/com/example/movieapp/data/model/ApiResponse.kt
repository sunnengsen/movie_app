package com.example.movieapp.data.model

class ApiResponse<T> (
    val status: String,
    val message: String,
    val data: T
) {
    fun isSuccessful(): Boolean {
        return status == "200"
    }

    fun isFailed(): Boolean {
        return status != "200"
    }

    fun isUnauthorized(): Boolean {
        return status == "401"
    }

    fun isForbidden(): Boolean {
        return status == "403"
    }

    fun isNotFound(): Boolean {
        return status == "404"
    }

    fun isInternalServerError(): Boolean {
        return status == "500"
    }

}