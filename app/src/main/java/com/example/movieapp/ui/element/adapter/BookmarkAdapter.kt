package com.example.movieapp.ui.element.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.Bookmark
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.ViewHolderBookmarkBinding
import com.example.movieapp.ui.element.activity.MovieDetail
import com.example.movieapp.ui.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso

class BookmarkAdapter(
    private val bookmarks: MutableList<Bookmark>,
    private val viewModel: ProfileViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding =
            ViewHolderBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.bind(bookmark, viewModel)
    }

    override fun getItemCount(): Int = bookmarks.size

    inner class BookmarkViewHolder(private val binding: ViewHolderBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmark: Bookmark, viewModel: ProfileViewModel) {
            binding.bookmarkMovieTitle.text = bookmark.movie.title
            binding.bookmarkMovieDescription.text = bookmark.movie.description
            Picasso.get().load(bookmark.movie.posterUrl).into(binding.bookmarkMoviePoster)

            // Click listener to movie detail
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, MovieDetail::class.java).apply {
                    putExtra("movieId", bookmark.movie.id)
                }
                context.startActivity(intent)
            }


            // Set click listener for remove button
            binding.removeBookmarkButton.setOnClickListener {
                val context = itemView.context
                viewModel.removeBookmark(context, bookmark.id)
                bookmarks.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                Toast.makeText(context, "Bookmark removed", Toast.LENGTH_SHORT).show()

            }
        }
    }
}