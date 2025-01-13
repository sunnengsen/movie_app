package com.example.movieapp.ui.element.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.MovieModel
import com.squareup.picasso.Picasso

class SlideAdapter(private val slideMovie: List<MovieModel>) :
    RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_slide, parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val movie = slideMovie[position]
        Picasso.get().load(movie.posterUrl).into(holder.movieImage)
        holder.movieTitle.text = movie.title
        holder.movieDes.text = movie.description
        holder.releaseDate.text = movie.releaseDate
    }

    override fun getItemCount(): Int {
        return slideMovie.size
    }

    class SlideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.slideImage)
        val movieTitle: TextView = itemView.findViewById(R.id.titleMovie)
        val movieDes: TextView = itemView.findViewById(R.id.des)
        val releaseDate: TextView = itemView.findViewById(R.id.hour)
    }

}