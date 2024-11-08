package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.models.ApiState
import com.example.movieapp.api.ApiManager
import com.example.movieapp.models.State
import com.example.movieapp.models.MovieResponse

class CategoryViewModel: ViewModel() {

    private val _movies = MutableLiveData<ApiState<List<Movie>>>()
    val movies: LiveData<ApiState<List<Movie>>>
        get() = _movies
    fun getMovies() {
        _movies.value = ApiState(state = State.loading, data = null)
        ApiManager.getInstance().getMovies { response: MutableList<out Movie>?, error: Exception? ->
            if (error != null) {
                _movies.value = ApiState(state = State.error, data = null)
                Log.e("MovieViewModel", "Error: $error")
            } else {
                _movies.value = ApiState(state = State.success, data = response)
                Log.d("MovieViewModel", "Movies: $response")
            }
        }
    }
}