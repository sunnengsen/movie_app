package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var directorAdapter: DirectorAdapter
    private var allActors: List<Actor> = listOf()
    private var allDirectors: List<Director> = listOf()

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

        actorAdapter = ActorAdapter(allActors)
        directorAdapter = DirectorAdapter(allDirectors)
        binding.recyclerViewActor.adapter = actorAdapter
        binding.recyclerViewDirector.adapter = directorAdapter

        viewModel.actor.observe(viewLifecycleOwner) { state ->
            handleState(state, binding.recyclerViewActor, binding.tvActor)
        }
        viewModel.director.observe(viewLifecycleOwner) { state ->
            handleStates(state, binding.recyclerViewDirector, binding.tvDirector)
        }

        viewModel.loadActor()
        viewModel.loadDirector()

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterActorsAndDirectors(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleState(state: ApiState<List<Actor>>, recyclerView: RecyclerView, textView: TextView) {
        when (state.status) {
            Status.SUCCESS -> {
                state.data?.let {
                    allActors = it
                    actorAdapter.updateData(it)
                }
                hideLoading()
                textView.visibility = if (state.data != null) View.VISIBLE else View.GONE
            }
            Status.ERROR -> {
                showAlert()
                textView.visibility = View.GONE
            }
            Status.LOADING -> {
                showLoading()
                textView.visibility = View.GONE
            }
        }
    }

    private fun handleStates(state: ApiState<List<Director>>, recyclerView: RecyclerView, textView: TextView) {
        when (state.status) {
            Status.SUCCESS -> {
                state.data?.let {
                    allDirectors = it
                    directorAdapter.updateData(it)
                }
                hideLoading()
                textView.visibility = if (state.data != null) View.VISIBLE else View.GONE
            }
            Status.ERROR -> {
                showAlert()
                textView.visibility = View.GONE
            }
            Status.LOADING -> {
                showLoading()
                textView.visibility = View.GONE
            }
        }
    }

    private fun filterActorsAndDirectors(query: String?) {
        val filteredActors = allActors.filter { it.name.contains(query ?: "", ignoreCase = true) }
        val filteredDirectors = allDirectors.filter { it.name.contains(query ?: "", ignoreCase = true) }
        actorAdapter.updateData(filteredActors)
        directorAdapter.updateData(filteredDirectors)
        binding.tvActorNotFound.visibility = if (filteredActors.isEmpty()) View.VISIBLE else View.GONE
        binding.tvDirectorNotFound.visibility = if (filteredDirectors.isEmpty()) View.VISIBLE else View.GONE
    }
}