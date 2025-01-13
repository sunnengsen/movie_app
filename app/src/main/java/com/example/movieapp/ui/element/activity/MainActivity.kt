package com.example.movieapp.ui.element.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.ui.element.fragment.ActorFragment
import com.example.movieapp.ui.element.fragment.MovieFragment
import com.example.movieapp.ui.element.fragment.HomeFragment
import com.example.movieapp.ui.element.fragment.ProfileFragment
import com.example.movieapp.ui.element.fragment.SearchFragment
import com.example.movieapp.ui.viewmodel.ActorDirectorViewModel
import com.example.movieapp.ui.viewmodel.AllMovieViewModel
import com.example.movieapp.ui.viewmodel.HomeViewModel
import com.example.movieapp.ui.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the status bar and title
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        supportActionBar?.hide()

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
                    R.id.actor -> {
                        replaceFragment(ActorFragment())
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
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}