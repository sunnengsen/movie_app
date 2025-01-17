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
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.api.client.ApiClient
import com.example.movieapp.data.model.ApiResponse
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.BookmarkRequest
import com.example.movieapp.data.model.BookmarkResponse
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.data.model.Status
import com.example.movieapp.ui.element.activity.MovieDetail
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SlideAdapter(private val slideMovie: List<MovieModel>) :
    RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_container_item, parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val movie = slideMovie[position]
        Picasso.get().load(movie.banner).into(holder.movieImage)
        holder.movieTitle.text = movie.title
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetail::class.java)
            intent.putExtra("movieId", movie.id)
            context.startActivity(intent)
        }
        holder.bookmarkButton.setOnClickListener {
            postBookmark(holder.itemView.context, movie)
        }
    }

    override fun getItemCount(): Int {
        return slideMovie.size
    }

    private fun postBookmark(context: Context, movie: MovieModel) {
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
    }
}