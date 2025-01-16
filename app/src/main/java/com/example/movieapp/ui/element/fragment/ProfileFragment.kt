package com.example.movieapp.ui.element.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentProfileBinding
import com.example.movieapp.ui.element.activity.LoginActivity
import com.example.movieapp.ui.element.activity.MainActivity
import com.example.movieapp.ui.element.activity.MovieDetail
import com.example.movieapp.ui.element.adapter.BookmarkAdapter
import com.example.movieapp.ui.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        sharedPreferences =
            requireContext().getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPreferences.getString("auth_token", null)

        if (authToken == null) {
            // User is not logged in, navigate to LoginActivity
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
            return null
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // User is logged in, load profile data
        viewModel.loadProfile(requireContext())

        viewModel.profileData.observe(viewLifecycleOwner) { state ->
            when (state.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    val profileData = state.data
                    if (profileData != null) {
                        hideLoading()
                        binding.profileName.text = profileData.username
                        binding.profileEmail.text = profileData.email
                        Picasso.get().load(profileData.profileUrl).into(binding.profileImage)

                        if (profileData.bookmarks.isNullOrEmpty()) {
                            binding.bookmarksTextView.visibility = View.GONE
                        } else {
                            binding.bookmarksTextView.visibility = View.VISIBLE
                            binding.recyclerViewBookmarks.layoutManager = GridLayoutManager(
                                requireContext(),
                                1,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.recyclerViewBookmarks.adapter =
                                BookmarkAdapter(profileData.bookmarks)
                        }
                    }
                }

                Status.ERROR -> {
                    // Show error message
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            with(sharedPreferences.edit()) {
                remove("auth_token")
                apply()
            }
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        binding.edit.setOnClickListener {
            with(sharedPreferences.edit()) {
                remove("auth_token")
                apply()
            }
            startActivity(Intent(requireContext(), MovieDetail::class.java))
            requireActivity().finish()
        }
    }
}