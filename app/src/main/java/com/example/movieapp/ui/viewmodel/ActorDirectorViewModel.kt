package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.Actor
import com.example.movieapp.data.model.ActorNDirectorData
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.Director
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActorDirectorViewModel: ViewModel() {
    private val _actor = MutableLiveData<ApiState<List<Actor>>>()
    private val _director = MutableLiveData<ApiState<List<Director>>>()
    val actor get() = _actor
    val director get() = _director

    fun loadActor() {
        _actor.postValue(ApiState.loading())

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Actor>> = ApiClient.get().apiService.loadActors()
                if (response.status == "200") {
                    _actor.postValue(ApiState.success(response.data))
                    Log.d("Actor", response.data.toString())
                } else {
                    _actor.postValue(ApiState.error(response.message ?: "Unknown error"))
                    Log.d("Actor", response.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _actor.postValue(ApiState.error(e.message ?: "Unknown error"))
            }
        }
    }
    fun loadDirector(){
        _director.postValue(ApiState.loading())

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Director>> = ApiClient.get().apiService.loadDirectors()
                if (response.status == "200") {
                    _director.postValue(ApiState.success(response.data))
                    Log.d("Director", response.data.toString())
                } else {
                    _director.postValue(ApiState.error(response.message ?: "Unknown error"))
                    Log.d("Director", response.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _director.postValue(ApiState.error(e.message ?: "Unknown error"))
            }
        }
    }
}