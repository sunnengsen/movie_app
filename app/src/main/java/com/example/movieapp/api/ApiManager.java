package com.example.movieapp.api;

import com.example.movieapp.models.Movie;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager instance;

    private ApiService apiService;

    private ApiManager(){}

    public static synchronized ApiManager getInstance() {
        if(instance == null) {
            instance = new ApiManager();
        }

        return instance;
    }

    public ApiService getApiService() {
        if(apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public void getMovies(@NotNull Function2<? super List<? extends Movie>, ? super Exception, ? extends Object> function) {
        getApiService().getMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    function.invoke(response.body(), null);
                } else {
                    function.invoke(null, new Exception("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                function.invoke(null, new Exception(t));
            }
        });
    }

    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }
}