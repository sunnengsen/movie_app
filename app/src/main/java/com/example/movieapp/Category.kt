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

class Category : Fragment() {
    private lateinit var categoryViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViews: RecyclerView
    private lateinit var recyclerViewss: RecyclerView

    private lateinit var categoryAdapter: MovieAdapter
    private lateinit var dramaAdapter: MovieAdapter
    private lateinit var movieAdapter: MovieAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerViews = view.findViewById(R.id.recyclerViews)
        recyclerViews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewss = view.findViewById(R.id.recyclerViewss)
        recyclerViewss.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categoryViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        categoryViewModel.movies.observe(viewLifecycleOwner, Observer { apiState ->
            if (apiState.state == State.success) {
                categoryAdapter = MovieAdapter(apiState.data ?: emptyList())
                recyclerView.adapter = categoryAdapter

                dramaAdapter = MovieAdapter(apiState.data ?: emptyList())
                recyclerViews.adapter = dramaAdapter

                movieAdapter = MovieAdapter(apiState.data ?: emptyList())
                recyclerViewss.adapter = movieAdapter
            }
        })
        categoryViewModel.getMovies()
    }
}