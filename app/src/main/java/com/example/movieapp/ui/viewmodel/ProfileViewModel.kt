package com.example.movieapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.ProfileData
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _profileData = MutableLiveData<ApiState<ProfileData>>()
    val profileData: LiveData<ApiState<ProfileData>> get() = _profileData

    fun loadProfile(context: Context) {
        _profileData.postValue(ApiState.loading())

        val sharedPreferences = context.getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        if (token != null) {
            viewModelScope.launch {
                try {
                    val response: ApiResponse<ProfileData> = ApiClient.get().apiService.loadProfile("Bearer $token")
                    if (response.status == "200") {
                        _profileData.postValue(ApiState.success(response.data))
                    } else {
                        _profileData.postValue(ApiState.error(response.message))
                    }
                } catch (e: Exception) {
                    _profileData.postValue(ApiState.error(e.message ?: "Unknown error"))
                }
            }
        } else {
            _profileData.postValue(ApiState.error("Token is missing"))
        }
    }
}