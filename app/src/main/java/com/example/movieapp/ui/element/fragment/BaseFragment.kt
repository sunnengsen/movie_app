package com.example.movieapp.ui.element.fragment

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

open class BaseFragment: Fragment() {
    private var progressDilog: ProgressDialog? = null


    fun showLoading() {
        if (progressDilog == null) {
            progressDilog = ProgressDialog(requireContext()).apply {
                setMessage("Loading...")
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                setProgressStyle(ProgressDialog.STYLE_SPINNER)
                setIndeterminate(true)
                setIndeterminateDrawable(requireContext().getDrawable(android.R.drawable.progress_indeterminate_horizontal))
            }
        }
        if (!progressDilog!!.isShowing) {
            progressDilog!!.show()
        }
    }

    fun hideLoading() {
        progressDilog?.takeIf { it.isShowing }?.dismiss()
    }


    fun showAlert(){

    }


}