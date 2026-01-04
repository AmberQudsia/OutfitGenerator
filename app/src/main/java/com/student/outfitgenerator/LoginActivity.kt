package com.student.outfitgenerator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvRegister: TextView
    
    // Hardcoded credentials for testing
    private val ADMIN_USERNAME = "admin"
    private val ADMIN_PASSWORD = "admin123"
    
    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_FULL_NAME = "fullName"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        initializeViews()
        setupClickListeners()
        animateViews()
    }
    
    private fun initializeViews() {
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        tvRegister = findViewById(R.id.tvRegister)
    }
    
    private fun animateViews() {
        // Fade in and scale animations
        val logo = findViewById<android.widget.ImageView>(R.id.ivLogo)
        
        logo.alpha = 0f
        logo.scaleX = 0.5f
        logo.scaleY = 0.5f
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .start()
        
        etUsername.alpha = 0f
        etUsername.translationY = 50f
        etUsername.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(200)
            .start()
        
        etPassword.alpha = 0f
        etPassword.translationY = 50f
        etPassword.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(300)
            .start()
        
        btnLogin.alpha = 0f
        btnLogin.scaleX = 0.9f
        btnLogin.scaleY = 0.9f
        btnLogin.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(400)
            .start()
    }
    
    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
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
            
            performLogin()
        }
        
        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }
    
    private fun performLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        
        // Validate inputs
        if (!validateInputs(username, password)) {
            return
        }
        
        // Check credentials against both admin and registered user
        if (isValidCredentials(username, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            
            // Save login session
            saveLoginSession(username)
            
            // Navigate to Home Activity with smooth transition
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show()
            
            // Shake animation on error
            etUsername.animate()
                .translationX(-10f)
                .setDuration(50)
                .withEndAction {
                    etUsername.animate()
                        .translationX(10f)
                        .setDuration(50)
                        .withEndAction {
                            etUsername.animate()
                                .translationX(0f)
                                .setDuration(50)
                                .start()
                        }
                        .start()
                }
                .start()
        }
    }
    
    private fun isValidCredentials(username: String, password: String): Boolean {
        // Check against hardcoded admin credentials
        if (username == ADMIN_USERNAME && password == ADMIN_PASSWORD) {
            return true
        }
        
        // Check against registered user credentials from SharedPreferences
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString(KEY_USERNAME, null)
        val savedPassword = sharedPreferences.getString(KEY_PASSWORD, null)
        
        return username == savedUsername && password == savedPassword
    }
    
    private fun saveLoginSession(username: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("logged_in_user", username)
        editor.putBoolean("is_logged_in", true)
        editor.apply()
    }
    
    private fun validateInputs(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            etUsername.error = getString(R.string.error_empty_username)
            etUsername.requestFocus()
            return false
        }
        
        if (password.isEmpty()) {
            etPassword.error = getString(R.string.error_empty_password)
            etPassword.requestFocus()
            return false
        }
        
        return true
    }
}
