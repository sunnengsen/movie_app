package com.example.movieapp.ui.element.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.BookmarkRequest
import com.example.movieapp.data.model.Movie
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
    private var isBookmarked: Boolean = false
    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra("movieId", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        }

        findViewById<ImageView>(R.id.play).setOnClickListener {
            val intent = Intent(this, PlayMovieActivity::class.java)
            intent.putExtra("MOVIE_URL", movieUrl)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.bookmarkButton).setOnClickListener {
            if (isBookmarked) {
                removeBookmark(movieId)
            } else {
                addBookmark(movieId)
            }
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
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
                        checkIfBookmarked(movieId)
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
                        randomAdapter.updateMovies(randomMovies)
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
        findViewById<TextView>(R.id.movieTypeName).text = movie.movieType.name?: "N/A"
        findViewById<TextView>(R.id.releaseDate).text = movie.releaseDate
        findViewById<TextView>(R.id.actorName).text = movie.actorName
        findViewById<TextView>(R.id.duration).text = movie.duration
        movieUrl = movie.movieUrl ?: ""
    }

    private fun checkIfBookmarked(movieId: Int) {
        val sharedPreferences = getSharedPreferences("MovieAppPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        val userId = sharedPreferences.getInt("user_id", -1)

        if (token != null && userId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.get().apiService.loadProfile(token)
                    withContext(Dispatchers.Main) {
                        if (response.status == "200") {
                            // Check if the movie is bookmarked
                            val bookmarks = response.data.bookmarks
                            isBookmarked = bookmarks?.any { it.movie.id == movieId } == true
                            updateBookmarkButton()
                        } else {
                            // Handle error
                        }
                    }
                } catch (e: Exception) {
                    // Handle exception
                }
            }
        } else {
            // Handle missing token or user ID
        }
    }

    private fun updateBookmarkButton() {
        val bookmarkButton = findViewById<ImageView>(R.id.bookmarkButton)
        if (isBookmarked) {
            bookmarkButton.setImageResource(R.drawable.baseline_bookmark_24)
        } else {
            bookmarkButton.setImageResource(R.drawable.ic_action_bookmark)
        }
    }

    private fun addBookmark(movieId: Int) {
        val sharedPreferences = getSharedPreferences("MovieAppPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        val userId = sharedPreferences.getInt("user_id", -1)

        if (token != null && userId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.get().apiService.bookmark(BookmarkRequest(userId, movieId))
                    withContext(Dispatchers.Main) {
                        if (response.status == "200") {
                            isBookmarked = true
                            updateBookmarkButton()
                            Toast.makeText(this@MovieDetail, "Bookmark added successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MovieDetail, "Failed to add bookmark", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MovieDetail, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Token or user ID is missing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeBookmark(movieId: Int) {
        val sharedPreferences = getSharedPreferences("MovieAppPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        val userId = sharedPreferences.getInt("user_id", -1)

        if (token != null && userId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.get().apiService.removeBookmark(BookmarkRequest(userId, movieId))
                    withContext(Dispatchers.Main) {
                        if (response.status == "200") {
                            isBookmarked = false
                            updateBookmarkButton()
                            Toast.makeText(this@MovieDetail, "Bookmark removed successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MovieDetail, "Failed to remove bookmark", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MovieDetail, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Token or user ID is missing", Toast.LENGTH_SHORT).show()
        }
    }
}