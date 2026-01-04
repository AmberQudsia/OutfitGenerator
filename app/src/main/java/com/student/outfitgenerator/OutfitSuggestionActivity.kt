package com.student.outfitgenerator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.student.outfitgenerator.api.ImageAnalyzer
import com.student.outfitgenerator.databinding.ActivityOutfitSuggestionBinding
import kotlinx.coroutines.launch

class OutfitSuggestionActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityOutfitSuggestionBinding
    private var selectedImageUri: Uri? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutfitSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupSpinners()
        setupClickListeners()
        animateViews()
    }
    
    private fun setupSpinners() {
        // Setup occasion spinner
        val occasionAdapter = android.widget.ArrayAdapter.createFromResource(
            this,
            R.array.occasion_options,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spOccasion.adapter = occasionAdapter
        
        // Setup climate spinner
        val climateAdapter = android.widget.ArrayAdapter.createFromResource(
            this,
            R.array.climate_options,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spClimate.adapter = climateAdapter
        
        // Setup budget spinner
        val budgetAdapter = android.widget.ArrayAdapter.createFromResource(
            this,
            R.array.budget_options,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spBudget.adapter = budgetAdapter
    }
    
    private fun animateViews() {
        // Fade in title
        AnimationHelper.fadeIn(binding.tvTitle, 200)
        
        // Slide in sections with staggered delays
        AnimationHelper.slideInFromBottom(binding.cardPhotoUpload, 100)
        AnimationHelper.slideInFromBottom(binding.cardOccasion, 200)
        AnimationHelper.slideInFromBottom(binding.cardClimate, 300)
        AnimationHelper.slideInFromBottom(binding.cardPreferences, 400)
        AnimationHelper.slideInFromBottom(binding.cardBudget, 500)
        
        // Scale in button
        AnimationHelper.scaleIn(binding.btnGetSuggestions, 600)
    }
    
    private fun setupClickListeners() {
        // Photo upload button
        binding.btnUploadPhoto.setOnClickListener {
            AnimationHelper.pressAnimation(binding.btnUploadPhoto)
            openImagePicker()
        }
        
        // Get suggestions button
        binding.btnGetSuggestions.setOnClickListener {
            AnimationHelper.pressAnimation(binding.btnGetSuggestions)
            generateSuggestions()
        }
        
        // Back button
        binding.btnBack.setOnClickListener {
            AnimationHelper.pressAnimation(binding.btnBack)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
    
    private fun openImagePicker() {
        // Simple implementation - in real app, you'd use proper image picker
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select Photo"),
                REQUEST_CODE_IMAGE_PICKER
            )
        } catch (e: Exception) {
            Toast.makeText(this, "No image picker available", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.tvPhotoStatus.text = "Photo selected ✓"
                binding.tvPhotoStatus.setTextColor(getColor(R.color.success))
            }
        }
    }
    
    private fun generateSuggestions() {
        // Validate inputs
        if (!validateInputs()) {
            return
        }
        
        // Collect all input data
        val occasion = binding.spOccasion.selectedItem.toString()
        val climate = binding.spClimate.selectedItem.toString()
        val preferredColors = binding.etPreferredColors.text.toString().trim()
        val brands = binding.etBrands.text.toString().trim()
        val budgetRange = binding.spBudget.selectedItem.toString()
        
        // Show loading
        binding.btnGetSuggestions.text = "Analyzing Image..."
        binding.btnGetSuggestions.isEnabled = false
        
        // Use coroutines for async API call
        lifecycleScope.launch {
            try {
                // Analyze image if provided
                val imageAnalysis = if (selectedImageUri != null) {
                    Toast.makeText(this@OutfitSuggestionActivity, "Analyzing photo with AI...", Toast.LENGTH_SHORT).show()
                    ImageAnalyzer.analyzeImage(this@OutfitSuggestionActivity, selectedImageUri!!, occasion, climate)
                } else {
                    null
                }
                
                // Create suggestion result with image analysis
                val suggestionResult = OutfitSuggestionResult(
                    occasion = occasion,
                    climate = climate,
                    preferredColors = preferredColors,
                    brands = brands,
                    budgetRange = budgetRange,
                    hasPhoto = selectedImageUri != null,
                    imageAnalysis = imageAnalysis
                )
                
                // Navigate to results
                val intent = Intent(this@OutfitSuggestionActivity, OutfitSuggestionResultActivity::class.java).apply {
                    putExtra("suggestion_result", suggestionResult)
                }
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                
            } catch (e: Exception) {
                // If API fails, show error and use mock suggestions
                Toast.makeText(this@OutfitSuggestionActivity, "API unavailable. Using smart suggestions...", Toast.LENGTH_LONG).show()
                
                val suggestionResult = OutfitSuggestionResult(
                    occasion = occasion,
                    climate = climate,
                    preferredColors = preferredColors,
                    brands = brands,
                    budgetRange = budgetRange,
                    hasPhoto = selectedImageUri != null,
                    imageAnalysis = null
                )
                
                val intent = Intent(this@OutfitSuggestionActivity, OutfitSuggestionResultActivity::class.java).apply {
                    putExtra("suggestion_result", suggestionResult)
                }
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            } finally {
                // Reset button
                binding.btnGetSuggestions.text = "✨ Get AI Suggestions"
                binding.btnGetSuggestions.isEnabled = true
            }
        }
    }
    
    private fun validateInputs(): Boolean {
        var isValid = true
        
        // Occasion is required
        if (binding.spOccasion.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select an occasion", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        
        // Climate is required
        if (binding.spClimate.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select climate/weather", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        
        return isValid
    }
    
    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 1001
    }
}

// Data class to hold suggestion results
data class OutfitSuggestionResult(
    val occasion: String,
    val climate: String,
    val preferredColors: String,
    val brands: String,
    val budgetRange: String,
    val hasPhoto: Boolean,
    val imageAnalysis: com.student.outfitgenerator.api.ImageAnalysisResult? = null
) : java.io.Serializable

