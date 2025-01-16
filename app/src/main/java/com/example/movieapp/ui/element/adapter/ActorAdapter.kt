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
import com.squareup.picasso.Picasso

class ActorAdapter(private val actors: List<Actor>) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private val expandedPositions = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]
        with(holder) {
            actorName.text = actor.name
            Picasso.get().load(actor.profileUrl).into(actorImage)
            actorBiography.text = actor.biography
            actorBirthDate.text = actor.birthDate

            val isExpanded = expandedPositions.contains(position)
            recyclerViewMovies.visibility = if (isExpanded) View.VISIBLE else View.GONE

            if (isExpanded) {
                val movieAdapter = MovieAdapter(actor.movies.map { it.movie })
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

    class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val actorImage: ImageView = itemView.findViewById(R.id.actorImage)
        val actorName: TextView = itemView.findViewById(R.id.actorName)
        val actorBiography: TextView = itemView.findViewById(R.id.biographyActor)
        val actorBirthDate: TextView = itemView.findViewById(R.id.birthDate)
        val recyclerViewMovies: RecyclerView = itemView.findViewById(R.id.recyclerViewMovies)
    }
}
