package com.example.movieapp.ui.element.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.BookmarkRequest
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.ui.element.activity.MovieDetail
import com.example.movieapp.ui.element.activity.PlayMovieActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SlideAdapter(private val slideMovie: List<MovieModel>, context: Context) :
    RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    private val bookmarkedMovieIds = mutableSetOf<Int>()
    private var currentUserId: Int = -1

    init {
        fetchBookmarks(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_container_item, parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val movie = slideMovie[position]
        Picasso.get().load(movie.banner).into(holder.movieImage)
        holder.movieTitle.text = movie.title
        holder.rating.text = movie.rating
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetail::class.java)
            intent.putExtra("movieId", movie.id)
            context.startActivity(intent)
        }
        updateBookmarkButton(holder, movie)
        holder.bookmarkButton.setOnClickListener {
            postBookmark(holder.itemView.context, movie, holder)
        }
        holder.playButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PlayMovieActivity::class.java)
            intent.putExtra("MOVIE_URL", movie.movieUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return minOf(slideMovie.size, 5)
    }

    fun fetchBookmarks(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        currentUserId = sharedPreferences.getInt("user_id", -1)

        if (token != null && currentUserId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.get().apiService.loadProfile(token)
                    withContext(Dispatchers.Main) {
                        if (response.status == "200") {
                            bookmarkedMovieIds.clear()
                            response.data.bookmarks?.let { bookmarks ->
                                bookmarkedMovieIds.addAll(bookmarks.map { it.movie.id })
                            }
                            notifyDataSetChanged()
                        } else {
                            Log.e("SlideAdapter", "Failed to fetch bookmarks: ${response.status}")
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("SlideAdapter", "Error: ${e.message}")
                    }
                }
            }
        } else {
            Log.e("SlideAdapter", "Token or User ID is missing")
            Toast.makeText(context, "Token or user ID is missing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateBookmarkButton(holder: SlideViewHolder, movie: MovieModel) {
        val context = holder.itemView.context
        if (bookmarkedMovieIds.contains(movie.id)) {
            holder.bookmarkButton.setColorFilter(ContextCompat.getColor(context, R.color.purple_200))
        } else {
            holder.bookmarkButton.setColorFilter(ContextCompat.getColor(context, R.color.white))
        }
    }

    private fun postBookmark(context: Context, movie: MovieModel, holder: SlideViewHolder) {
        val sharedPreferences = context.getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        val userId = sharedPreferences.getInt("user_id", -1)
        val movieId = movie.id

        if (token != null && userId != -1) {
            Log.d("SlideAdapter", "Token: $token, User ID: $userId, Movie ID: $movieId ")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.get().apiService.bookmark(BookmarkRequest(userId, movieId))
                    withContext(Dispatchers.Main) {
                        if (response.status == "200") {
                            bookmarkedMovieIds.add(movieId)
                            updateBookmarkButton(holder, movie)
                            Toast.makeText(context, "Bookmark added successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e("SlideAdapter", "Failed to add bookmark: ${response.status}")
                            Toast.makeText(context, "Failed to add bookmark: ${response.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Log.e("SlideAdapter", "Token or User ID is missing")
            Toast.makeText(context, "Token or user ID is missing", Toast.LENGTH_SHORT).show()
        }
    }

    class SlideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.slideImage)
        val movieTitle: TextView = itemView.findViewById(R.id.titleMovie)
        val bookmarkButton: ImageView = itemView.findViewById(R.id.bookmarkButton)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val playButton: TextView = itemView.findViewById(R.id.watchNowButton) // Add this line
    }
}