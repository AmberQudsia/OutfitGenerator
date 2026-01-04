package com.student.outfitgenerator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    
    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_FULL_NAME = "fullName"
        private const val KEY_EMAIL = "email"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        
        initializeViews()
        setupClickListeners()
        animateViews()
    }
    
    private fun initializeViews() {
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)
    }
    
    private fun animateViews() {
        // Fade in and scale animations
        val logo = findViewById<android.widget.ImageView>(R.id.ivLogo)
        val title = findViewById<TextView>(R.id.tvRegisterTitle)
        
        logo.alpha = 0f
        logo.scaleX = 0.5f
        logo.scaleY = 0.5f
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .start()
        
        title.alpha = 0f
        title.translationY = -20f
        title.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(200)
            .start()
        
        // Animate input fields
        val fields = listOf(etFullName, etEmail, etUsername, etPassword, etConfirmPassword)
        fields.forEachIndexed { index, field ->
            field.alpha = 0f
            field.translationY = 30f
            field.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(250L + (index * 50L))
                .start()
        }
        
        btnRegister.alpha = 0f
        btnRegister.scaleX = 0.9f
        btnRegister.scaleY = 0.9f
        btnRegister.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(600)
            .start()
    }
    
    private fun setupClickListeners() {
        btnRegister.setOnClickListener {
            // Press animation
            it.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction {
                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }
                .start()
            
            performRegistration()
        }
        
        tvLogin.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
    
    private fun performRegistration() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()
        
        // Validate all inputs
        if (!validateInputs(fullName, email, username, password, confirmPassword)) {
            return
        }
        
        // Save user credentials to SharedPreferences
        saveUserCredentials(fullName, email, username, password)
        
        // Registration successful
        Toast.makeText(this, getString(R.string.success_registration), Toast.LENGTH_SHORT).show()
        
        // Navigate to Login Activity with smooth transition
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
    
    private fun saveUserCredentials(fullName: String, email: String, username: String, password: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        editor.putString(KEY_FULL_NAME, fullName)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }
    
    private fun validateInputs(
        fullName: String,
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true
        
        // Validate full name
        if (fullName.isEmpty()) {
            etFullName.error = getString(R.string.error_empty_full_name)
            etFullName.requestFocus()
            isValid = false
        }
        
        // Validate email
        if (email.isEmpty()) {
            etEmail.error = getString(R.string.error_empty_email)
            if (isValid) etEmail.requestFocus()
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.error_invalid_email)
            if (isValid) etEmail.requestFocus()
            isValid = false
        }
        
        // Validate username
        if (username.isEmpty()) {
            etUsername.error = getString(R.string.error_empty_username)
            if (isValid) etUsername.requestFocus()
            isValid = false
        }
        
        // Validate password
        if (password.isEmpty()) {
            etPassword.error = getString(R.string.error_empty_password)
            if (isValid) etPassword.requestFocus()
            isValid = false
        } else if (password.length < 6) {
            etPassword.error = getString(R.string.error_password_length)
            if (isValid) etPassword.requestFocus()
            isValid = false
        }
        
        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.error = getString(R.string.error_empty_password)
            if (isValid) etConfirmPassword.requestFocus()
            isValid = false
        } else if (password != confirmPassword) {
            etConfirmPassword.error = getString(R.string.error_password_mismatch)
            if (isValid) etConfirmPassword.requestFocus()
            isValid = false
        }
        
        // Shake animation on error
        if (!isValid) {
            etFullName.animate()
                .translationX(-10f)
                .setDuration(50)
                .withEndAction {
                    etFullName.animate()
                        .translationX(10f)
                        .setDuration(50)
                        .withEndAction {
                            etFullName.animate()
                                .translationX(0f)
                                .setDuration(50)
                                .start()
                        }
                        .start()
                }
                .start()
        }
        
        return isValid
    }
}
