package com.student.outfitgenerator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.student.outfitgenerator.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHomeBinding
    
    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_FULL_NAME = "fullName"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        displayUserName()
        setupClickListeners()
        animateViews()
    }
    
    private fun animateViews() {
        // Fade in title and welcome message
        AnimationHelper.fadeIn(binding.tvHomeTitle, 200)
        AnimationHelper.fadeIn(binding.tvWelcomeMessage, 300)
        
        // Slide in cards with staggered delays
        AnimationHelper.slideInFromBottom(binding.cardUploadClothes, 100)
        AnimationHelper.slideInFromBottom(binding.cardMyWardrobe, 200)
        AnimationHelper.slideInFromBottom(binding.cardGetSuggestions, 300)
        AnimationHelper.slideInFromBottom(binding.cardAISuggestions, 350)
        
        // Fade in features section
        AnimationHelper.fadeIn(binding.tvFeaturesTitle, 400)
        AnimationHelper.fadeIn(binding.tvFeature1, 450)
        AnimationHelper.fadeIn(binding.tvFeature2, 500)
        AnimationHelper.fadeIn(binding.tvFeature3, 550)
    }
    
    private fun displayUserName() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val fullName = sharedPreferences.getString(KEY_FULL_NAME, null)
        
        if (fullName != null) {
            binding.tvWelcomeMessage.text = "Welcome back, $fullName! Ready to style your outfit?"
        }
    }
    
    private fun setupClickListeners() {
        binding.btnLogout.setOnClickListener {
            AnimationHelper.pressAnimation(binding.btnLogout)
            logout()
        }
        
        // Card click listeners with animations
        binding.cardUploadClothes.setOnClickListener {
            AnimationHelper.pressAnimation(binding.cardUploadClothes)
            val intent = Intent(this, UploadClothesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        
        binding.cardMyWardrobe.setOnClickListener {
            AnimationHelper.pressAnimation(binding.cardMyWardrobe)
            val intent = Intent(this, WardrobeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        
        binding.cardGetSuggestions.setOnClickListener {
            AnimationHelper.pressAnimation(binding.cardGetSuggestions)
            val intent = Intent(this, SuggestionsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        
        binding.cardAISuggestions.setOnClickListener {
            AnimationHelper.pressAnimation(binding.cardAISuggestions)
            val intent = Intent(this, OutfitSuggestionActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }
    
    private fun logout() {
        // Clear login session
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", false)
        editor.putString("logged_in_user", null)
        editor.apply()
        
        // Navigate back to Login Activity with smooth transition
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
    
    override fun onBackPressed() {
        // Prevent going back to login after successful login
        // User must use logout button
        Toast.makeText(this, "Use logout button to exit", Toast.LENGTH_SHORT).show()
    }
}

