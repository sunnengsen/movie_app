package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.auth.LoginRequest
import com.example.movieapp.data.model.auth.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginData = MutableLiveData<ApiState<LoginResponse>>()
    val loginData: LiveData<ApiState<LoginResponse>> get() = _loginData

    fun login(email: String, password: String) {
        _loginData.postValue(ApiState.loading())

        viewModelScope.launch {
            try {
                val response: ApiResponse<LoginResponse> = ApiClient.get().apiService.login(LoginRequest(email, password))
                if (response.status == "200") {
                    _loginData.postValue(ApiState.success(response.data))
                } else {
                    _loginData.postValue(ApiState.error(response.message))
                }
            } catch (e: Exception) {
                _loginData.postValue(ApiState.error(e.message ?: "Unknown error"))
            }
        }
    }
}