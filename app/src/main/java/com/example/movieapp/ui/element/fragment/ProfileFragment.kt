package com.example.movieapp.ui.element.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.model.ProfileData
import com.example.movieapp.data.model.Status
import com.example.movieapp.databinding.FragmentProfileBinding
import com.example.movieapp.ui.element.activity.LoginActivity
import com.example.movieapp.ui.element.activity.MainActivity
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
                        binding.profileName.setText(profileData.username)
                        binding.profileEmail.setText(profileData.email)

                        if (profileData.profileUrl.isNullOrEmpty()) {
                            binding.profileImage.setImageResource(R.drawable.background) // Use your default image resource
                        } else {
                            Picasso.get().load(profileData.profileUrl).into(binding.profileImage)
                        }

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
                            binding.recyclerViewBookmarks.adapter = BookmarkAdapter(profileData.bookmarks.toMutableList(), viewModel)
                        }
                    }
                }

                Status.ERROR -> {
                    // Show error message
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
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
            binding.profileName.isEnabled = true
            binding.profileEmail.isEnabled = true
            binding.saveButton.visibility = View.VISIBLE
        }

        binding.saveButton.setOnClickListener {
            val name = binding.profileName.text.toString().takeIf { it.isNotBlank() }
            val email = binding.profileEmail.text.toString().takeIf { it.isNotBlank() }
            val profileData = ProfileData(
                userId = 1, // Replace with actual userId
                username = name,
                email = email,
                password = null, // Password is not being updated
                role = null, // Role is not being updated
                profileUrl = null, // Profile URL is not being updated
                bookmarks = null // Bookmarks are not being updated
            )

            viewModel.updateProfile(requireContext(), profileData)

            binding.profileName.isEnabled = false
            binding.profileEmail.isEnabled = false
            binding.saveButton.visibility = View.GONE
        }
    }
}