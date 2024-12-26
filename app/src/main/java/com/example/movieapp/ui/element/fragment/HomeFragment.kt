package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.HomeData
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.element.adapter.LatestAdapter
import com.example.movieapp.ui.viewmodel.HomeViewModel

class HomeFragment: BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

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
            else -> {}
        }
    }

    private fun showHomeData(homeData: HomeData) {
        val adapter = LatestAdapter(homeData.latestMovies)
        binding.recyclerView.adapter = adapter
    }
}