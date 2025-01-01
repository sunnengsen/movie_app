package com.example.movieapp.ui.element.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.Bookmark
import com.example.movieapp.databinding.ViewHolderBookmarkBinding
import com.squareup.picasso.Picasso

class BookmarkAdapter(private val bookmarks: List<Bookmark>) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ViewHolderBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.bind(bookmark)
    }

    override fun getItemCount(): Int = bookmarks.size

    inner class BookmarkViewHolder(private val binding: ViewHolderBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmark: Bookmark) {
            binding.bookmarkMovieTitle.text = bookmark.movie.title
            binding.bookmarkMovieDescription.text = bookmark.movie.description
            Picasso.get().load(bookmark.movie.posterUrl).into(binding.bookmarkMoviePoster)
        }
    }
}