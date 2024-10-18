// Category.kt
package com.example.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapters.CategoryAdapter
import com.example.movieapp.services.CategoryService

class Category : Fragment() {

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var progressBar: ProgressBar
    private val categoryService = CategoryService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        categoryRecyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)

        loadCategories()

        return view
    }

    private fun loadCategories() {
        progressBar.visibility = View.VISIBLE
        // Simulate loading data
        categoryRecyclerView.postDelayed({
            val categories = categoryService.getCategories()
            categoryAdapter = CategoryAdapter(categories)
            categoryRecyclerView.adapter = categoryAdapter
            progressBar.visibility = View.GONE
        }, 1000) // Simulate a delay for loading data
    }
}