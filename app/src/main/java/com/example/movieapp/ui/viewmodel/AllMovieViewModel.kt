package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllMovieViewModel : ViewModel() {
    private val _dramaMovies = MutableLiveData<ApiState<List<Movie>>>()
    val dramaMovies get() = _dramaMovies

    private val _actionMovies = MutableLiveData<ApiState<List<Movie>>>()
    val actionMovies get() = _actionMovies

    fun loadDrama() {
        var apiState = ApiState.loading<List<Movie>>()
        _dramaMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Drama data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading drama data: ${response.message}")
                }
                _dramaMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _dramaMovies.postValue(apiState)
            }
        }
    }

    fun loadAction() {
        var apiState = ApiState.loading<List<Movie>>()
        _actionMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadActionMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Action data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading action data: ${response.message}")
                }
                _actionMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _actionMovies.postValue(apiState)
            }
        }
    }
}