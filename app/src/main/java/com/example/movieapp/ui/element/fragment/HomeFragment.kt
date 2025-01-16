package com.example.movieapp.ui.element.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.ApiState
import com.example.movieapp.data.model.HomeData
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.element.adapter.LatestAdapter
import com.example.movieapp.ui.element.adapter.RecommendAdapter
import com.example.movieapp.ui.element.adapter.SlideAdapter
import com.example.movieapp.ui.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {
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

        binding.recyclerViewLatest.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.recyclerViewRandom.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.recyclerViewTop.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.homeData.observe(viewLifecycleOwner) { state ->
            handleState(
                state,
                binding.recyclerViewLatest,
                binding.latestMovies,
                binding.randomMovies,
                binding.topMovies,
                binding.viewAllLatest,
                binding.viewAllRandom,
                binding.viewAllTop
            )
        }
        viewModel.loadHomeData()
    }

    private fun handleState(
        state: ApiState<HomeData>,
        recyclerView: RecyclerView,
        latestMoviesTextView: TextView,
        randomMoviesTextView: TextView,
        topMoviesTextView: TextView,
        viewAllLatestTextView: TextView,
        viewAllRandomTextView: TextView,
        viewAllTopTextView: TextView
    ) {
        val viewsToToggle = listOf(
            latestMoviesTextView,
            randomMoviesTextView,
            topMoviesTextView,
            viewAllLatestTextView,
            viewAllRandomTextView,
            viewAllTopTextView
        )

        when (state.status) {
            Status.SUCCESS -> {
                state.data?.let {
                    showHomeData(it)
                }
                hideLoading()
                viewsToToggle.forEach { it.visibility = View.VISIBLE }
            }
            Status.ERROR -> {
                showAlert()
                viewsToToggle.forEach { it.visibility = View.GONE }
            }
            Status.LOADING -> {
                showLoading()
                viewsToToggle.forEach { it.visibility = View.GONE }
            }
        }
    }

    private fun showHomeData(homeData: HomeData) {
        binding.recyclerViewLatest.adapter = LatestAdapter(homeData.latestMovies)
        binding.recyclerViewRandom.adapter = RecommendAdapter(homeData.randomMovies)
        binding.recyclerViewTop.adapter = RecommendAdapter(homeData.topMovies)
        slideAdapter = SlideAdapter(homeData.slides)
        binding.viewPagerSlide.adapter = slideAdapter

        handler.postDelayed(autoScrollRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
    }
}