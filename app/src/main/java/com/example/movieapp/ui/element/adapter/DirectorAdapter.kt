package com.example.movieapp.ui.element.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.Actor
import com.example.movieapp.data.model.Director
import com.squareup.picasso.Picasso

class DirectorAdapter(private val actors: List<Director>) : RecyclerView.Adapter<DirectorAdapter.DirectorViewHolder>() {

    private val expandedPositions = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return DirectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        val director = actors[position]
        with(holder) {
            actorName.text = director.name
            Picasso.get().load(director.profileUrl).into(actorImage)
            actorBiography.text = director.biography
            actorBirthDate.text = director.birthDate

            val isExpanded = expandedPositions.contains(position)
            recyclerViewMovies.visibility = if (isExpanded) View.VISIBLE else View.GONE

            if (isExpanded) {
                val movieAdapter = MovieAdapter(director.movies.map { it.movie })
                recyclerViewMovies.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewMovies.adapter = movieAdapter
            }

            itemView.setOnClickListener {
                if (isExpanded) {
                    expandedPositions.remove(position)
                } else {
                    expandedPositions.add(position)
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = actors.size

    class DirectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val actorImage: ImageView = itemView.findViewById(R.id.actorImage)
        val actorName: TextView = itemView.findViewById(R.id.actorName)
        val actorBiography: TextView = itemView.findViewById(R.id.biographyActor)
        val actorBirthDate: TextView = itemView.findViewById(R.id.birthDate)
        val recyclerViewMovies: RecyclerView = itemView.findViewById(R.id.recyclerViewMovies)
    }
}
