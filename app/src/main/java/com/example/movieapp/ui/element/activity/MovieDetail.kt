package com.example.movieapp.ui.element.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetail : AppCompatActivity() {

    private lateinit var movieUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("movieId", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        }

        findViewById<ImageView>(R.id.play).setOnClickListener {
            val intent = Intent(this, PlayMovieActivity::class.java)
            intent.putExtra("MOVIE_URL", movieUrl)
            startActivity(intent)
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.get().apiService.getMovieDetails(movieId)
                if (response.status == "200") {
                    val movie = response.data
                    withContext(Dispatchers.Main) {
                        displayMovieDetails(movie)
                    }
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        Picasso.get().load(movie.posterUrl).into(findViewById<ImageView>(R.id.movieImage))
        findViewById<TextView>(R.id.titleMovie).text = movie.title
        findViewById<TextView>(R.id.des).text = movie.description
        findViewById<TextView>(R.id.rating).text = movie.rating
        findViewById<TextView>(R.id.movieTypeName).text = movie.movieTypeName?.name ?: "N/A"
        findViewById<TextView>(R.id.releaseDate).text = movie.releaseDate
        movieUrl = movie.movieUrl ?: ""
    }
}