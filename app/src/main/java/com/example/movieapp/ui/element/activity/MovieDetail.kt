package com.example.movieapp.ui.element.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.ui.element.adapter.RecommendAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetail : AppCompatActivity() {

    private lateinit var movieUrl: String
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var randomAdapter: RecommendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movieId", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        }

        findViewById<ImageView>(R.id.play).setOnClickListener {
            val intent = Intent(this, PlayMovieActivity::class.java)
            intent.putExtra("MOVIE_URL", movieUrl)
            startActivity(intent)
        }

        setupRandomMoviesRecyclerView()
    }

    private fun setupRandomMoviesRecyclerView() {
        randomAdapter = RecommendAdapter(emptyList())
        binding.recyclerViewRandom.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRandom.adapter = randomAdapter
    }

    private fun fetchMovieDetails(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.get().apiService.getMovieDetails(movieId)
                if (response.status == "200") {
                    val movie = response.data
                    withContext(Dispatchers.Main) {
                        displayMovieDetails(movie)
                        fetchRandomMovies()
                    }
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun fetchRandomMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.get().apiService.loadHomeData()
                if (response.status == "200") {
                    val randomMovies = response.data.randomMovies
                    withContext(Dispatchers.Main) {
                        randomAdapter.updateData(randomMovies)
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
        Picasso.get().load(movie.posterUrl).into(findViewById<ImageView>(R.id.moviePoster))
        Picasso.get().load(movie.banner).into(findViewById<ImageView>(R.id.movieImage))
        findViewById<TextView>(R.id.titleMovie).text = movie.title
        findViewById<TextView>(R.id.des).text = movie.description
        findViewById<TextView>(R.id.rating).text = movie.rating
        findViewById<TextView>(R.id.movieTypeName).text = movie.movieTypeName?.name ?: "N/A"
        findViewById<TextView>(R.id.releaseDate).text = movie.releaseDate
        movieUrl = movie.movieUrl ?: ""
    }
}