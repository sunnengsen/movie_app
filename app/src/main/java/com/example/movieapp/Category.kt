// Category.kt
package com.example.movieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapters.CategoryAdapter
import com.example.movieapp.services.CategoryService

class Category : Fragment() {

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryService = CategoryService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        categoryRecyclerView = view.findViewById(R.id.recyclerView)
        categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
        categoryAdapter = CategoryAdapter(categoryService.getCategories())
        categoryRecyclerView.adapter = categoryAdapter

        return view
    }
}