package com.example.movieapp.data.model

data class ApiState<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(): ApiState<T> = ApiState(Status.LOADING, null, "Loading data")
        fun <T> success(data: T): ApiState<T> = ApiState(Status.SUCCESS, data, "Data loaded successfully")
        fun <T> error(message: String): ApiState<T> = ApiState(Status.ERROR, null, "Error loading data: $message")
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}