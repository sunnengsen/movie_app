package com.example.movieapp.ui.element.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movieapp.R
import com.example.movieapp.data.model.Status
import com.example.movieapp.ui.element.fragment.ProfileFragment
import com.example.movieapp.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.loginButton)
        val backButton: ImageView = findViewById(R.id.backButton)

        // Navigate to ProfileActivity when the back button is clicked
        backButton.setOnClickListener {
            navigateToProfile()
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.login(username, password)
        }

        observeLoginData()
    }

    private fun observeLoginData() {
        loginViewModel.loginData.observe(this, Observer { state ->
            when (state.status) {
                Status.LOADING -> {
                    // Show loading indicator
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    val token = state.data?.token
                    if (token != null) {
                        // Store the token in SharedPreferences
                        val sharedPreferences = getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString("auth_token", token)
                            apply()
                        }
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        // Navigate to the next screen
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login Failed: Token is null", Toast.LENGTH_SHORT).show()
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Login Failed: ${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun navigateToProfile() {
        val intent = Intent(this, ProfileFragment::class.java)
        startActivity(intent)
        finish() // Optional: Finish LoginActivity if you don't want it in the back stack
    }

    override fun onBackPressed() {
        // Route to ProfileActivity when the back button is pressed
        navigateToProfile()
    }
}