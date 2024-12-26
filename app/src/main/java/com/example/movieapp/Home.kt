package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.HomeData
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.element.adapter.LatestAdapter
import com.example.movieapp.ui.element.adapter.RecommendAdapter
import com.example.movieapp.ui.viewmodel.HomeViewModel

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewLatest.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRandom.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.homeData.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
        viewModel.loadHomeData()
    }

    private fun handleState(state: ApiState<HomeData>) {
        when (state.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> {
                hideLoading()
                showHomeData(state.data!!)
            }
            Status.ERROR -> {
                hideLoading()
                showAlert()
            }
        }
    }

    private fun showHomeData(homeData: HomeData) {
        val latestAdapter = LatestAdapter(homeData.latestMovies)
        binding.recyclerViewLatest.adapter = latestAdapter

        val recommendAdapter = RecommendAdapter(homeData.randomMovies)
        binding.recyclerViewRandom.adapter = recommendAdapter
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