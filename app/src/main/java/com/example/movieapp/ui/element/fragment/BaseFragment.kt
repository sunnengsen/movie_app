package com.example.movieapp.ui.element.fragment

import android.view.View
import androidx.fragment.app.Fragment
import com.example.movieapp.ui.element.activity.MainActivity

open class BaseFragment : Fragment() {

    fun showLoading() {
        (activity as? MainActivity)?.showLoading()
    }

    fun hideLoading() {
        (activity as? MainActivity)?.hideLoading()
    }

    // Call this method after the backend operation completes (success or failure)
    fun onBackendComplete(success: Boolean) {
        if (success) {
            hideLoading()  // Hide loading if backend operation is successful
        } else {
            showAlert()  // Show alert if backend operation failed
        }
    }

    fun showAlert() {
        // Implement alert logic here
    }
}