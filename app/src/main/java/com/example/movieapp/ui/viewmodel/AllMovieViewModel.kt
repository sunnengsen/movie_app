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

    private val _comedyMovies = MutableLiveData<ApiState<List<Movie>>>()
    val comedyMovies get() = _comedyMovies

    private val _thrillerMovies = MutableLiveData<ApiState<List<Movie>>>()
    val thrillerMovies get() = _thrillerMovies

    private val _fantasyMovies = MutableLiveData<ApiState<List<Movie>>>()
    val fantasyMovies get() = _fantasyMovies

    private val _horrorMovies = MutableLiveData<ApiState<List<Movie>>>()
    val horrorMovies get() = _horrorMovies

    private val _musicalMovies = MutableLiveData<ApiState<List<Movie>>>()
    val musicalMovies get() = _musicalMovies

    private val _romanceMovies = MutableLiveData<ApiState<List<Movie>>>()
    val romanceMovies get() = _romanceMovies

    private val _adventureMovies = MutableLiveData<ApiState<List<Movie>>>()
    val adventureMovies get() = _adventureMovies

    private val _allMovies = MutableLiveData<ApiState<List<Movie>>>()
    val allMovies get() = _allMovies


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

    fun loadComedy() {
        var apiState = ApiState.loading<List<Movie>>()
        _comedyMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadComedyMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Comedy data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading comedy data: ${response.message}")
                }
                _comedyMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _comedyMovies.postValue(apiState)
            }
        }
    }

    fun loadThriller() {
        var apiState = ApiState.loading<List<Movie>>()
        _thrillerMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadThrillerMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Thriller data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading thriller data: ${response.message}")
                }
                _thrillerMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _thrillerMovies.postValue(apiState)
            }
        }
    }

    fun loadFantasy() {
        var apiState = ApiState.loading<List<Movie>>()
        _fantasyMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadFantasyMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Fantasy data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading fantasy data: ${response.message}")
                }
                _fantasyMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _fantasyMovies.postValue(apiState)
            }
        }
    }

    fun loadHorror() {
        var apiState = ApiState.loading<List<Movie>>()
        _horrorMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadHorrorMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Horror data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading horror data: ${response.message}")
                }
                _horrorMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _horrorMovies.postValue(apiState)
            }
        }
    }

    fun loadMusical() {
        var apiState = ApiState.loading<List<Movie>>()
        _musicalMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadMusicalMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Musical data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading musical data: ${response.message}")
                }
                _musicalMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _musicalMovies.postValue(apiState)
            }
        }
    }

    fun loadRomance() {
        var apiState = ApiState.loading<List<Movie>>()
        _romanceMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadRomanceMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Romance data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading romance data: ${response.message}")
                }
                _romanceMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _romanceMovies.postValue(apiState)
            }
        }
    }

    fun loadAdventure() {
        var apiState = ApiState.loading<List<Movie>>()
        _adventureMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadAdventureMovies()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("Movie", "Adventure data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("Movie", "Error loading adventure data: ${response.message}")
                }
                _adventureMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("HomeViewModel", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _adventureMovies.postValue(apiState)
            }
        }
    }

    fun loadAllMovie() {
        var apiState = ApiState.loading<List<Movie>>()
        _allMovies.postValue(apiState)

        viewModelScope.launch {
            try {
                val response: ApiResponse<List<Movie>> = ApiClient.get().apiService.loadAllMovie()
                if (response.status == "200") {
                    apiState = ApiState.success(response.data)
                    Log.d("AllMovie", "All movie data loaded successfully: ${response.data}")
                } else {
                    apiState = ApiState.error(response.message)
                    Log.d("AllMovie", "Error loading all movie data: ${response.message}")
                }
                _allMovies.postValue(apiState)
            } catch (e: Exception) {
                apiState = e.message?.let { ApiState.error(it) }!!
                Log.d("AllMovie", "Exception: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                _allMovies.postValue(apiState)
            }
        }
    }
    fun searchMovie(title: String) {
        _allMovies.value = ApiState.loading()
        viewModelScope.launch {
            try {
                val apiService = ApiClient.get().apiService
                val response = apiService.searchMovie(title)
                if (response.status == "200") {
                    _allMovies.value = ApiState.success(response.data)
                } else {
                    _allMovies.value = ApiState.error(response.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _allMovies.value = ApiState.error(e.message ?: "Unknown error")
            }
        }
    }


}