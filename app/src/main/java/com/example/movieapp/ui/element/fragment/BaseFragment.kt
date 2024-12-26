package com.example.movieapp.ui.element.fragment

import android.app.ProgressDialog
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    private var progressDilog: ProgressDialog? = null


    fun showLoading(){
        if (progressDilog == null){
            progressDilog = ProgressDialog(requireContext())

        }
        progressDilog!!.show()

    }

    fun hideLoading(){
        progressDilog?.hide()

    }

    fun showAlert(){

    }


}