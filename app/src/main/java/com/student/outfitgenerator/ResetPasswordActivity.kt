package com.student.outfitgenerator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResetPasswordActivity : AppCompatActivity() {
    
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmNewPassword: EditText
    private lateinit var btnResetPassword: Button
    private lateinit var tvBackToLogin: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        
        initializeViews()
        setupClickListeners()
        animateViews()
    }
    
    private fun initializeViews() {
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
    }
    
    private fun animateViews() {
        val logo = findViewById<android.widget.ImageView>(R.id.ivLogo)
        val title = findViewById<TextView>(R.id.tvResetPasswordTitle)
        
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
        
        // Password fields animation
        val fields = listOf(etNewPassword, etConfirmNewPassword)
        fields.forEachIndexed { index, field ->
            field.alpha = 0f
            field.translationY = 30f
            field.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(300L + (index * 100L))
                .start()
        }
        
        // Button animation
        btnResetPassword.alpha = 0f
        btnResetPassword.scaleX = 0.9f
        btnResetPassword.scaleY = 0.9f
        btnResetPassword.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(500)
            .start()
    }
    
    private fun setupClickListeners() {
        btnResetPassword.setOnClickListener {
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
            
            resetPassword()
        }
        
        tvBackToLogin.setOnClickListener {
            navigateToLogin()
        }
    }
    
    private fun resetPassword() {
        val newPassword = etNewPassword.text.toString().trim()
        val confirmNewPassword = etConfirmNewPassword.text.toString().trim()
        
        // Validate passwords
        if (!validatePasswords(newPassword, confirmNewPassword)) {
            // Shake animation on error
            etNewPassword.animate()
                .translationX(-10f)
                .setDuration(50)
                .withEndAction {
                    etNewPassword.animate()
                        .translationX(10f)
                        .setDuration(50)
                        .withEndAction {
                            etNewPassword.animate()
                                .translationX(0f)
                                .setDuration(50)
                                .start()
                        }
                        .start()
                }
                .start()
            return
        }
        
        // Password reset successful
        Toast.makeText(this, getString(R.string.success_password_reset), Toast.LENGTH_SHORT).show()
        
        // Navigate to Login Activity
        navigateToLogin()
    }
    
    private fun validatePasswords(newPassword: String, confirmNewPassword: String): Boolean {
        var isValid = true
        
        // Validate new password
        if (newPassword.isEmpty()) {
            etNewPassword.error = getString(R.string.error_empty_password)
            etNewPassword.requestFocus()
            isValid = false
        } else if (newPassword.length < 6) {
            etNewPassword.error = getString(R.string.error_password_length)
            etNewPassword.requestFocus()
            isValid = false
        }
        
        // Validate confirm password
        if (confirmNewPassword.isEmpty()) {
            etConfirmNewPassword.error = getString(R.string.error_empty_password)
            if (isValid) etConfirmNewPassword.requestFocus()
            isValid = false
        } else if (newPassword != confirmNewPassword) {
            etConfirmNewPassword.error = getString(R.string.error_password_mismatch)
            if (isValid) etConfirmNewPassword.requestFocus()
            isValid = false
        }
        
        return isValid
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
