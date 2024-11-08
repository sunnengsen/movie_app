package com.example.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapters.MovieAdapter
import com.example.movieapp.models.State
import com.example.movieapp.viewmodel.MovieViewModel

class Home : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.movies.observe(viewLifecycleOwner, Observer { apiState ->
            if (apiState.state == State.success) {
                movieAdapter = MovieAdapter(apiState.data ?: emptyList())
                recyclerView.adapter = movieAdapter
            }
        })
        movieViewModel.getMovies()
    }
}