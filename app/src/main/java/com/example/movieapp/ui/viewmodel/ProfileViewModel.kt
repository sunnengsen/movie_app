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
import android.util.Log

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

    fun updateProfile(context: Context, profileData: ProfileData): LiveData<ApiState<ProfileData>> {
        val result = MutableLiveData<ApiState<ProfileData>>()
        val sharedPreferences = context.getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        if (token != null) {
            viewModelScope.launch {
                try {
                    Log.d("ProfileViewModel", "Updating profile with data: $profileData")
                    val response: ApiResponse<ProfileData> = ApiClient.get().apiService.updateProfile("Bearer $token", profileData)
                    if (response.status == "200") {
                        result.postValue(ApiState.success(response.data))
                        // Refetch the profile data
                        loadProfile(context)
                    } else {
                        result.postValue(ApiState.error(response.message))
                    }
                } catch (e: Exception) {
                    result.postValue(ApiState.error(e.message ?: "Unknown error"))
                }
            }
        } else {
            result.postValue(ApiState.error("Token is missing"))
        }

        return result
    }

    fun removeBookmark(context: Context, movieId: Int): LiveData<ApiState<ProfileData>> {
        val result = MutableLiveData<ApiState<ProfileData>>()
        val sharedPreferences = context.getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        if (token != null) {
            viewModelScope.launch {
                try {
                    val response: ApiResponse<ProfileData> = ApiClient.get().apiService.deleteBookmark("Bearer $token", movieId)
                    if (response.status == "200") {
                        result.postValue(ApiState.success(response.data))
                        // Refetch the profile data
                        loadProfile(context)
                    } else {
                        result.postValue(ApiState.error(response.message))
                    }
                } catch (e: Exception) {
                    result.postValue(ApiState.error(e.message ?: "Unknown error"))
                }
            }
        } else {
            result.postValue(ApiState.error("Token is missing"))
        }

        return result
    }
}