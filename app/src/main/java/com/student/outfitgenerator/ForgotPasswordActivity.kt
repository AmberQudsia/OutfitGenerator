package com.student.outfitgenerator

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {
    
    private lateinit var etEmail: EditText
    private lateinit var btnSendResetLink: Button
    private lateinit var tvBackToLogin: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        
        initializeViews()
        setupClickListeners()
        animateViews()
    }
    
    private fun initializeViews() {
        etEmail = findViewById(R.id.etEmail)
        btnSendResetLink = findViewById(R.id.btnSendResetLink)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
    }
    
    private fun animateViews() {
        val logo = findViewById<android.widget.ImageView>(R.id.ivLogo)
        val title = findViewById<TextView>(R.id.tvForgotPasswordTitle)
        val desc = findViewById<TextView>(R.id.tvForgotPasswordDesc)
        
        // Logo animation
        logo.alpha = 0f
        logo.scaleX = 0.5f
        logo.scaleY = 0.5f
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .start()
        
        // Title animation
        title.alpha = 0f
        title.translationY = -20f
        title.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(200)
            .start()
        
        // Description animation
        desc.alpha = 0f
        desc.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(300)
            .start()
        
        // Email field animation
        etEmail.alpha = 0f
        etEmail.translationY = 30f
        etEmail.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(400)
            .start()
        
        // Button animation
        btnSendResetLink.alpha = 0f
        btnSendResetLink.scaleX = 0.9f
        btnSendResetLink.scaleY = 0.9f
        btnSendResetLink.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(500)
            .start()
    }
    
    private fun setupClickListeners() {
        btnSendResetLink.setOnClickListener {
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
            
            sendResetLink()
        }
        
        tvBackToLogin.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
    
    private fun sendResetLink() {
        val email = etEmail.text.toString().trim()
        
        // Validate email
        if (!validateEmail(email)) {
            // Shake animation on error
            etEmail.animate()
                .translationX(-10f)
                .setDuration(50)
                .withEndAction {
                    etEmail.animate()
                        .translationX(10f)
                        .setDuration(50)
                        .withEndAction {
                            etEmail.animate()
                                .translationX(0f)
                                .setDuration(50)
                                .start()
                        }
                        .start()
                }
                .start()
            return
        }
        
        // Simulate sending reset link
        Toast.makeText(this, getString(R.string.success_reset_link_sent), Toast.LENGTH_SHORT).show()
        
        // Navigate to Reset Password Activity with smooth transition
        val intent = Intent(this, ResetPasswordActivity::class.java)
        intent.putExtra("EMAIL", email)
        startActivity(intent)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
    
    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            etEmail.error = getString(R.string.error_empty_email)
            etEmail.requestFocus()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.error_invalid_email)
            etEmail.requestFocus()
            return false
        }
        return true
    }
}
