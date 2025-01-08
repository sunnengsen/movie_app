package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.auth.LoginResponse
import com.example.movieapp.data.model.auth.SignUpRequest
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _signupData = MutableLiveData<ApiState<LoginResponse>>()
    val signupData: MutableLiveData<ApiState<LoginResponse>>
        get() = _signupData

    fun signup(email: String, password: String, username: String, role: String) {
        _signupData.postValue(ApiState.loading())

        viewModelScope.launch {
            try {
                val response: ApiResponse<LoginResponse> = ApiClient.get().apiService.signUp(
                    SignUpRequest(email, password, username, role)
                )
                if (response.status == "200") {
                    _signupData.postValue(ApiState.success(response.data))
                } else {
                    _signupData.postValue(ApiState.error(response.message))
                }
            }catch (e: Exception) {
                _signupData.postValue(ApiState.error(e.message ?: "Error loading data"))
            }
        }
    }
}