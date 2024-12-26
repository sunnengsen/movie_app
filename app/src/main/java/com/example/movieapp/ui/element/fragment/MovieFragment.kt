package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentCategoryBinding
import com.example.movieapp.ui.element.adapter.MovieAdapter
import com.example.movieapp.ui.viewmodel.AllMovieViewModel

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewModel: AllMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AllMovieViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewDrama.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAction.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.dramaMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewDrama)
        }

        viewModel.actionMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewAction)
        }

        viewModel.loadDrama()
        viewModel.loadAction()
    }

    private fun handleState(state: ApiState<List<Movie>>, recyclerView: RecyclerView) {
        when (state.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> {
                hideLoading()
                showMovieData(state.data!!, recyclerView)
            }
            Status.ERROR -> {
                hideLoading()
                showAlert()
            }
        }
    }

    private fun showMovieData(data: List<Movie>, recyclerView: RecyclerView) {
        val adapter = MovieAdapter(data)
        recyclerView.adapter = adapter
    }

    private fun showLoading() {
        // Implement loading UI
    }

    private fun hideLoading() {
        // Hide loading UI
    }

    private fun showAlert() {
        // Show error alert
    }
}