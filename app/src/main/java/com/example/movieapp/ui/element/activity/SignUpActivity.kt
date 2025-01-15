package com.example.movieapp.ui.element.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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

        val usernameEditText: EditText = findViewById(R.id.username)
        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val comfirmEditText: EditText = findViewById(R.id.comfirm_password)
        val signUpButton: Button = findViewById(R.id.buttonSignUp)
        val back: ImageView = findViewById(R.id.back)
        val Login: TextView = findViewById(R.id.login)

        back.setOnClickListener {
            navigateToLogin()
        }

        Login.setOnClickListener {
            navigateToLogin()
        }

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirm_password = comfirmEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm_password) {
                Toast.makeText(this, "Password and Confirm Password must be same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signUpViewModel.signup(email, password, username)
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

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigate_to_home", true)
        startActivity(intent)
        finish() // Optional: Finish LoginActivity if you don't want it in the back stack
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}