package com.example.movieapp.ui.element.fragment

import android.content.Context
import android.content.Intent
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
import com.example.movieapp.ui.element.activity.SignUpActivity
import com.example.movieapp.ui.element.adapter.BookmarkAdapter
import com.example.movieapp.ui.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadProfile(requireContext())

        viewModel.profileData.observe(viewLifecycleOwner) { state ->
            when (state.status) {
                Status.LOADING -> {
                    // Show loading indicator
                }
                Status.SUCCESS -> {
                    val profileData = state.data
                    if (profileData != null) {
                        binding.profileName.text = profileData.username
                        binding.profileRole.text = profileData.role
                        binding.profileEmail.text = profileData.email
                        Picasso.get().load(profileData.profileUrl).into(binding.profileImage)

                        binding.recyclerViewBookmarks.layoutManager = GridLayoutManager(requireContext(), 1, LinearLayoutManager.VERTICAL, false)
                        binding.recyclerViewBookmarks.adapter = BookmarkAdapter(profileData.bookmarks ?: emptyList())
                    }
                }
                Status.ERROR -> {
                    // Show error message
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                remove("auth_token")
                apply()
            }
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.buttonSignUp.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                remove("auth_token")
                apply()
            }
            startActivity(Intent(requireContext(), SignUpActivity::class.java))
            requireActivity().finish()
        }

        binding.logoutButton.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                remove("auth_token")
                apply()
            }
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }
}