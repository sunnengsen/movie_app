package com.example.movieapp.ui.element.activity

import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // Find the ImageView for the loader
        val loader = findViewById<ImageView>(R.id.loader)

        // Start the animation
        (loader.drawable as? Animatable)?.start()
    }

    companion object {
        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, LoadingActivity::class.java)
            activity.startActivity(intent)
        }

        fun stop(activity: AppCompatActivity) {
            activity.finish()
        }
    }
}