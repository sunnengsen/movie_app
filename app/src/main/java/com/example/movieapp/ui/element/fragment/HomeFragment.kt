package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.movieapp.ui.element.adapter.SlideAdapter
import com.example.movieapp.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var slideAdapter: SlideAdapter
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = slideAdapter.itemCount
            val currentItem = binding.viewPagerSlide.currentItem
            val nextItem = (currentItem + 1) % itemCount
            binding.viewPagerSlide.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 3000)
        }
    }

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
        binding.recyclerViewTop.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        viewModel.homeData.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
        viewModel.loadHomeData()
    }

    private fun handleState(state: ApiState<HomeData>) {
        when (state.status) {
            Status.LOADING -> {
                showLoading()
            }
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

        val topAdapter = RecommendAdapter(homeData.topMovies)
        binding.recyclerViewTop.adapter = topAdapter

        slideAdapter = SlideAdapter(homeData.slides)
        binding.viewPagerSlide.adapter = slideAdapter

        handler.postDelayed(autoScrollRunnable, 3000)
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

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
    }
}