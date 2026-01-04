package com.student.outfitgenerator

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var loadingText: TextView
    private val splashDelay: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize views
        progressBar = findViewById(R.id.splashProgressBar)
        loadingText = findViewById(R.id.splashLoadingText)

        // Start progress bar animation
        animateProgressBar()

        // Navigate to Login Activity after delay
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToLogin()
        }, splashDelay)
    }

    private fun animateProgressBar() {
        // Animate progress bar from 0 to 100
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animator.duration = splashDelay - 200 // Slightly shorter than delay
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        // Optional: Animate loading text
        animateLoadingText()
    }

    private fun animateLoadingText() {
        val handler = Handler(Looper.getMainLooper())
        var dotCount = 0
        val loadingTexts = arrayOf("Loading", "Loading.", "Loading..", "Loading...")

        val runnable = object : Runnable {
            override fun run() {
                loadingText.text = loadingTexts[dotCount % loadingTexts.size]
                dotCount++
                handler.postDelayed(this, 500) // Update every 500ms
            }
        }
        handler.post(runnable)

        // Stop animation after splash delay
        handler.postDelayed({
            handler.removeCallbacks(runnable)
        }, splashDelay)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close splash activity so user can't go back to it
        
        // Optional: Add fade transition
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    // Disable back button on splash screen
    override fun onBackPressed() {
        // Do nothing - prevent user from going back
    }
}


