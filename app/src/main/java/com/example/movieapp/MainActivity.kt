package com.example.movieapp

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        replaceFragment(Home())
                        true
                    }
                    R.id.categories -> {
                        replaceFragment(Category())
                        true
                    }
                    R.id.bookmarks -> {
                        replaceFragment(Bookmark())
                        true
                    }
                    R.id.test -> {
                        replaceFragment(Test())
                        true
                    }
                    R.id.profile -> {
                        replaceFragment(Profile())
                        true
                    }
                    else -> false
                }
            }
        MovieViewModel().getMovies()
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}