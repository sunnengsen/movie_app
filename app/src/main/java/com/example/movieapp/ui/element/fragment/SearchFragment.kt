package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.ui.element.adapter.MovieAdapter
import com.example.movieapp.ui.viewmodel.AllMovieViewModel

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: AllMovieViewModel
    private lateinit var adapter: MovieAdapter
    private var allMovies: List<Movie> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AllMovieViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerAllMovies.layoutManager = GridLayoutManager(context, 3)
        adapter = MovieAdapter(allMovies)
        binding.recyclerAllMovies.adapter = adapter

        viewModel.allMovies.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }

        viewModel.loadAllMovie()

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterMovies(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleState(state: ApiState<List<Movie>>) {
        when (state.status) {
            Status.LOADING -> {
                showLoading()
                Log.d("SearchFragment", "Loading data...")
            }
            Status.SUCCESS -> {
                hideLoading()
                Log.d("SearchFragment", "Data loaded successfully: ${state.data}")
                allMovies = state.data!!
                showMovieData(allMovies)
            }
            Status.ERROR -> {
                hideLoading()
                Log.e("SearchFragment", "Error loading data: ${state.message}")
                showAlert()
            }
        }
    }

    private fun showMovieData(data: List<Movie>) {
        adapter.updateData(data)
    }

    private fun filterMovies(query: String) {
        val filteredMovies = allMovies.filter { movie ->
            movie.title.contains(query, ignoreCase = true)
        }
        showMovieData(filteredMovies)
    }

}