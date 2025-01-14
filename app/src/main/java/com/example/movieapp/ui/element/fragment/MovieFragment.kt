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
        binding.recyclerViewComedy.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewThriller.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFantasy.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHorror.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMusical.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRomance.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAdventure.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAll.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewModel.dramaMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewDrama)
        }

        viewModel.actionMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewAction)
        }

        viewModel.comedyMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewComedy)
        }

        viewModel.thrillerMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewThriller)
        }

        viewModel.fantasyMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewFantasy)
        }

        viewModel.horrorMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewHorror)
        }

        viewModel.musicalMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewMusical)
        }

        viewModel.romanceMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewRomance)
        }

        viewModel.adventureMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewAdventure)
        }

        viewModel.allMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewAll)
        }

        viewModel.loadDrama()
        viewModel.loadAction()
        viewModel.loadComedy()
        viewModel.loadThriller()
        viewModel.loadFantasy()
        viewModel.loadHorror()
        viewModel.loadMusical()
        viewModel.loadRomance()
        viewModel.loadAdventure()
        viewModel.loadAllMovie()

    }

    private fun handleState(state: ApiState<List<Movie>>, recyclerView: RecyclerView) {
        when (state.status) {
            Status.LOADING -> {
                showLoading()
            }
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