package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class MovieFragment : BaseFragment() {
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

        viewModel.dramaMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewDrama, binding.tvShow)
        }

        viewModel.actionMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewAction, binding.action)
        }

        viewModel.comedyMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewComedy, binding.comedy)
        }

        viewModel.thrillerMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewThriller, binding.thriller)
        }

        viewModel.fantasyMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewFantasy, binding.fantasy)
        }

        viewModel.horrorMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewHorror, binding.horror)
        }

        viewModel.musicalMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewMusical, binding.musical)
        }

        viewModel.romanceMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewRomance, binding.romance)
        }

        viewModel.adventureMovies.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewAdventure, binding.adventure)
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
    }

    private fun handleState(state: ApiState<List<Movie>>, recyclerView: RecyclerView, textView: TextView) {
        when (state.status) {
            Status.LOADING -> {
                showLoading()  // Show the loading spinner when the status is LOADING
                textView.visibility = View.GONE  // Hide any error or info text
            }
            Status.SUCCESS -> {
                hideLoading()  // Hide loading only when the data is successfully loaded
                showMovieData(state.data!!, recyclerView)  // Show the data in the RecyclerView
                textView.visibility = if (state.data.isEmpty()) View.GONE else View.VISIBLE  // Show text if data is available
            }
            Status.ERROR -> {
                // Do not hide the loading spinner when the status is ERROR
                showAlert()  // Show an error alert
                textView.visibility = View.GONE  // Hide any error or info text
            }
        }
    }

    private fun showMovieData(data: List<Movie>, recyclerView: RecyclerView) {
        val adapter = MovieAdapter(data)
        recyclerView.adapter = adapter
    }

}