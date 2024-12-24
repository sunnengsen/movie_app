package com.example.movieapp.api;

import com.example.movieapp.models.Movie;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/movie")
    Call<List<Movie>> getMovies();
}