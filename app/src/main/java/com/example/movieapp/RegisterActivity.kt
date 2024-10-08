package com.example.movieapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageUrl = "https://res.cloudinary.com/dzogfvwih/image/upload/v1705904914/p10745606_p_v13_bh_d73mtp.jpg"

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}