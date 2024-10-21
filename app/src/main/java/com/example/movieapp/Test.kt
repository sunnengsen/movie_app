package com.example.movieapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import android.widget.TextView

class Test : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test, container, false)

        val loader = view.findViewById<View>(R.id.loader)
        val loadingText = view.findViewById<TextView>(R.id.loadingText) // Reference to the TextView

        val reverseRotationAnimator = ObjectAnimator.ofFloat(loader, "rotation", 360f, 0f)
        reverseRotationAnimator.duration = 1500  // Set rotation speed (1.5 seconds for full rotation)
        reverseRotationAnimator.repeatCount = ObjectAnimator.INFINITE  // Repeat indefinitely
        reverseRotationAnimator.start()

        val fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in_out)
        loadingText.startAnimation(fadeAnimation)



        return view
    }
}
