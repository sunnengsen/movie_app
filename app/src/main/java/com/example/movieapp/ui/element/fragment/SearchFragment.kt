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
                if (s.isNullOrEmpty()) {
                    viewModel.loadAllMovie()
                } else {
                    viewModel.searchMovie(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleState(state: ApiState<List<Movie>>) {
        when (state.status) {
            Status.SUCCESS -> {
                showMovieData(state.data ?: emptyList())
                hideLoading()
                binding.noResultsTextView.visibility = if (state.data.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
            Status.ERROR -> {
                Log.e("SearchFragment", state.message ?: "Unknown error")
                showAlert()
                binding.noResultsTextView.visibility = View.GONE
            }
            Status.LOADING -> {
                showLoading()
                Log.d("SearchFragment", "Loading")
                binding.noResultsTextView.visibility = View.GONE
            }
        }
    }

    private fun showMovieData(data: List<Movie>) {
        adapter.updateData(data)
    }
}