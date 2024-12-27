package com.example.movieapp.ui.element.activity

import android.os.Bundle

import androidx.fragment.app.Fragment

import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.ui.element.fragment.MovieFragment
import com.example.movieapp.ui.element.fragment.HomeFragment
import com.example.movieapp.ui.element.fragment.ProfileFragment
import com.example.movieapp.ui.element.fragment.SearchFragment
import com.example.movieapp.ui.viewmodel.AllMovieViewModel
import com.example.movieapp.ui.viewmodel.HomeViewModel
import com.example.movieapp.ui.viewmodel.MovieViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        replaceFragment(HomeFragment())
                        true
                    }
                    R.id.categories -> {
                        replaceFragment(MovieFragment())
                        true
                    }
                    R.id.bookmarks -> {
                        replaceFragment(SearchFragment())
                        true
                    }
                    R.id.test -> {
                        replaceFragment(SearchFragment())
                        true
                    }
                    R.id.profile -> {
                        replaceFragment(ProfileFragment())
                        true
                    }
                    else -> false
                }
            }

        HomeViewModel().loadHomeData()
        MovieViewModel().loadMovieData()
        AllMovieViewModel().loadAllMovie()
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}