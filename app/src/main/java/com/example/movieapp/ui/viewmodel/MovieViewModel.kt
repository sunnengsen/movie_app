// MovieViewModel.kt
package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.api.service.ApiService
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.MovieData
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movieData = MutableLiveData<ApiState<MovieData>>()
    val movieData: LiveData<ApiState<MovieData>> = _movieData

    fun loadMovieData() {
        _movieData.value = ApiState.loading()
        viewModelScope.launch {
            try {
                val apiService = ApiClient.get().apiService
                val response = apiService.loadMovieData()
                Log.d("MovieViewModel", "API Response: $response")
                if (response.status == "200") {
                    _movieData.value = ApiState.success(response.data)
                    Log.d("MovieViewModel", "Data loaded successfully: ${response.data}")
                } else {
                    _movieData.value = ApiState.error(response.message ?: "Unknown error")
                    Log.d("MovieViewModel", "Error: ${response.message}")
                }
            } catch (e: Exception) {
                _movieData.value = ApiState.error(e.message ?: "Unknown error")
                Log.d("MovieViewModel", "Error loading data: ${e.message}")
            }
        }
    }
}