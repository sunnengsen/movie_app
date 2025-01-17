package com.example.movieapp.ui.element.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.ActivityViewAllBinding
import com.example.movieapp.ui.element.adapter.RecommendAdapter
import com.example.movieapp.data.model.MovieModel

class ViewAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllBinding
    private lateinit var adapter: RecommendAdapter
    private var allMovies: List<MovieModel> = listOf()
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category") ?: return
        allMovies = intent.getParcelableArrayListExtra<MovieModel>("movies") ?: return

        binding.title.text = category.capitalize()

        setupRecyclerView()
        displayMovies(allMovies)
        setupSearch()
    }

    private fun setupRecyclerView() {
        adapter = RecommendAdapter(emptyList())
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapter
    }

    private fun displayMovies(movies: List<MovieModel>) {
        val filteredMovies = when (category) {
            "latest" -> movies.sortedByDescending { it.releaseDate }
            "top" -> movies.sortedByDescending { it.rating }
            else -> movies
        }
        adapter.updateMovies(filteredMovies)
    }

    private fun setupSearch() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredMovies = if (s.isNullOrEmpty()) {
                    when (category) {
                        "latest" -> allMovies.sortedByDescending { it.releaseDate }
                        "top" -> allMovies.sortedByDescending { it.rating }
                        else -> allMovies
                    }
                } else {
                    when (category) {
                        "latest" -> allMovies.filter { it.title.contains(s, ignoreCase = true) }.sortedByDescending { it.releaseDate }
                        "top" -> allMovies.filter { it.title.contains(s, ignoreCase = true) }.sortedByDescending { it.rating }
                        else -> allMovies.filter { it.title.contains(s, ignoreCase = true) }
                    }
                }
                displayMovies(filteredMovies)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}