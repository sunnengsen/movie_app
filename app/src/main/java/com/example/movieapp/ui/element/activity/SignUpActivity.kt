package com.example.movieapp.ui.element.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movieapp.R
import com.example.movieapp.data.model.Status
import com.example.movieapp.ui.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameEditText: EditText = findViewById(R.id.userName)
        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val roleEditText: EditText = findViewById(R.id.role)
        val signUpButton: Button = findViewById(R.id.buttonRegister)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val role = roleEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            signUpViewModel.signup(email, password, username, role)
        }
        observeSignUpData()
    }

    private fun observeSignUpData() {
        signUpViewModel.signupData.observe(this, Observer { state ->
            when (state.status) {

                Status.LOADING -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }

                Status.SUCCESS -> {
                    val token = state.data?.token
                    if (token != null) {
                        //Store token in shared preferences
                        val sharedPreferences = getSharedPreferences("MovieAppPrefs", MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString("auth_token", token)
                            apply()
                        }
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                        //Navigate to home activity
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(this, "SignUp Failed : ${state.message} ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}