package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.Actor
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.Director
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentActorBinding
import com.example.movieapp.ui.element.adapter.ActorAdapter
import com.example.movieapp.ui.element.adapter.DirectorAdapter
import com.example.movieapp.ui.viewmodel.ActorDirectorViewModel

class ActorFragment: BaseFragment() {
    private lateinit var binding: FragmentActorBinding
    private lateinit var viewModel: ActorDirectorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActorBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ActorDirectorViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewActor.layoutManager = GridLayoutManager(context, 1)
        binding.recyclerViewDirector.layoutManager = GridLayoutManager(context, 1)

        viewModel.actor.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewActor)
        }
        viewModel.director.observe(viewLifecycleOwner) { state ->
            handleStates(state, binding.recyclerViewDirector)
        }

        viewModel.loadActor()
        viewModel.loadDirector()
    }

    private fun handleState(state: ApiState<List<Actor>>, recyclerView: RecyclerView) {
        when (state.status) {
            Status.LOADING -> {
                showLoading()
            }
            Status.SUCCESS -> {
                hideLoading()
                showActorData(state.data!!, recyclerView)
            }
            Status.ERROR -> {
                hideLoading()
                showAlert()
            }
        }
    }

    private fun handleStates(state: ApiState<List<Director>>, recyclerView: RecyclerView) {
        when (state.status) {
            Status.LOADING -> {
                showLoading()
            }
            Status.SUCCESS -> {
                hideLoading()
                showDirectorData(state.data!!, recyclerView)
            }
            Status.ERROR -> {
                hideLoading()
                showAlert()
            }
        }
    }

    private fun showActorData(actors: List<Actor>, recyclerView: RecyclerView) {
        val adapter = ActorAdapter(actors)
        recyclerView.adapter = adapter
    }

    private fun showDirectorData(directors: List<Director>, recyclerView: RecyclerView) {
        val adapter = DirectorAdapter(directors)
        recyclerView.adapter = adapter
    }
}