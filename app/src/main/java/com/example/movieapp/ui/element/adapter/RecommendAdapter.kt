package com.example.movieapp.ui.element.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.ui.element.activity.MovieDetail
import com.squareup.picasso.Picasso

class RecommendAdapter(private val randomMovies: List<MovieModel>) :
    RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item, parent, false)
        return RecommendViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val movie = randomMovies[position]
        holder.movieTitle.text = movie.rating
        Picasso.get().load(movie.posterUrl).into(holder.movieImage)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetail::class.java)
            intent.putExtra("movieId", movie.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return randomMovies.size
    }

    class RecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
    }
}