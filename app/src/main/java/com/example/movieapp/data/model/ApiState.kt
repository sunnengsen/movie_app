package com.example.movieapp.data.model

data class ApiState<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
){
    companion object{
        fun <T> loading(): ApiState<T> {
            return ApiState(Status.LOADING, null, null)
        }

        fun <T> success(data: T): ApiState<T> {
            return ApiState(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String): ApiState<T> {
            return ApiState(Status.ERROR, null, message)
        }

    }
}
enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
