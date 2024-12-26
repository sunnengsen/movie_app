package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.HomeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {

    private val _homeData = MutableLiveData<ApiState<HomeData>>()
    val homeData get() = _homeData

    fun loadHomeData() {
        var apiState = ApiState.loading<HomeData>()
        _homeData.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<HomeData> = ApiClient.get().apiService.loadHomeData()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("HomeViewModel", "Data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("HomeViewModel", "Error loading data: ${response.message}")
                }
                _homeData.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _homeData.postValue(apiState)
            }
        }
    }
}