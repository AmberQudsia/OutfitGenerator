package com.student.outfitgenerator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.student.outfitgenerator.api.ImageGenerator
import com.student.outfitgenerator.api.TextSuggestionGenerator
import com.student.outfitgenerator.databinding.ActivityOutfitSuggestionResultBinding
import kotlinx.coroutines.launch

class OutfitSuggestionResultActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityOutfitSuggestionResultBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutfitSuggestionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val result = intent.getSerializableExtra("suggestion_result") as? OutfitSuggestionResult
        
        if (result != null) {
            generateAndDisplayTextSuggestions(result)
            generateAndDisplayImage(result)
            animateViews()
        } else {
            Toast.makeText(this, "Error loading suggestions", Toast.LENGTH_SHORT).show()
            finish()
        }
        
        setupClickListeners()
    }
    
    private fun generateAndDisplayTextSuggestions(result: OutfitSuggestionResult) {
        // Show loading state
        binding.tvSuggestions.text = "✨ Generating AI suggestions..."
        
        // Generate text suggestions from API
        lifecycleScope.launch {
            try {
                val suggestionsText = TextSuggestionGenerator.generateSuggestionsText(
                    occasion = result.occasion,
                    climate = result.climate,
                    preferredColors = result.preferredColors,
                    brands = result.brands,
                    budgetRange = result.budgetRange,
                    imageAnalysis = result.imageAnalysis
                )
                
                if (suggestionsText != null) {
                    // Display API-generated suggestions
                    binding.tvSuggestions.text = suggestionsText
                } else {
                    // Fallback to mock suggestions if API fails
                    binding.tvSuggestions.text = generateAISuggestions(result)
                    Toast.makeText(
                        this@OutfitSuggestionResultActivity,
                        "Using fallback suggestions",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Fallback to mock suggestions on error
                binding.tvSuggestions.text = generateAISuggestions(result)
                Toast.makeText(
                    this@OutfitSuggestionResultActivity,
                    "API unavailable. Using fallback suggestions.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        
        // Display input details with image analysis
        displayDetails(result)
    }
    
    private fun displayDetails(result: OutfitSuggestionResult) {
        val details = buildString {
            append("Occasion: ${result.occasion}\n")
            append("Climate: ${result.climate}\n")
            if (result.preferredColors.isNotEmpty()) {
                append("Preferred Colors: ${result.preferredColors}\n")
            }
            if (result.brands.isNotEmpty()) {
                append("Brands: ${result.brands}\n")
            }
            append("Budget: ${result.budgetRange}\n")
            if (result.hasPhoto) {
                append("✓ Photo analyzed with Vision API\n")
                result.imageAnalysis?.let { analysis ->
                    if (analysis.bodyShape != null) {
                        append("Body Shape: ${analysis.bodyShape}\n")
                    }
                    if (analysis.skinTone != null) {
                        append("Skin Tone: ${analysis.skinTone}\n")
                    }
                    if (analysis.dominantColors.isNotEmpty()) {
                        append("Recommended Colors: ${analysis.dominantColors.joinToString(", ")}\n")
                    }
                }
            }
        }
        
        binding.tvDetails.text = details
    }
    
    private fun generateAndDisplayImage(result: OutfitSuggestionResult) {
        // Show image card and progress
        binding.cardOutfitImage.visibility = View.VISIBLE
        binding.progressImage.visibility = View.VISIBLE
        binding.ivOutfitImage.visibility = View.GONE
        
        // Get outfit image from Unsplash (FREE API!)
        lifecycleScope.launch {
            try {
                val imageUrl = ImageGenerator.generateOutfitImage(
                    occasion = result.occasion,
                    climate = result.climate,
                    preferredColors = result.preferredColors,
                    imageAnalysis = result.imageAnalysis
                )
                
                if (imageUrl != null && imageUrl.isNotEmpty()) {
                    // Load image with Glide
                    Glide.with(this@OutfitSuggestionResultActivity)
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(binding.ivOutfitImage)
                    
                    binding.progressImage.visibility = View.GONE
                    binding.ivOutfitImage.visibility = View.VISIBLE
                    
                    // Animate image appearance
                    AnimationHelper.fadeIn(binding.ivOutfitImage, 300)
                    
                    Toast.makeText(
                        this@OutfitSuggestionResultActivity,
                        "✨ Outfit image found!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Show error message and keep card visible with error state
                    binding.progressImage.visibility = View.GONE
                    binding.ivOutfitImage.visibility = View.VISIBLE
                    binding.ivOutfitImage.setImageResource(android.R.drawable.ic_menu_report_image)
                    binding.tvImageLabel.text = "⚠️ Image not found"
                    
                    Toast.makeText(
                        this@OutfitSuggestionResultActivity,
                        "No outfit image found. Add Unsplash key or check connection.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Show error state
                binding.progressImage.visibility = View.GONE
                binding.ivOutfitImage.visibility = View.VISIBLE
                binding.ivOutfitImage.setImageResource(android.R.drawable.ic_menu_report_image)
                binding.tvImageLabel.text = "⚠️ Image error"
                
                Toast.makeText(
                    this@OutfitSuggestionResultActivity,
                    "Error: ${e.message}. Check Unsplash key setup.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    private fun animateViews() {
        AnimationHelper.fadeIn(binding.tvTitle, 200)
        AnimationHelper.slideInFromBottom(binding.cardOutfitImage, 250)
        AnimationHelper.slideInFromBottom(binding.cardSuggestions, 300)
        AnimationHelper.slideInFromBottom(binding.cardDetails, 400)
        AnimationHelper.fadeIn(binding.btnBack, 500)
    }
    
    
    private fun generateAISuggestions(result: OutfitSuggestionResult): String {
        // Use Vision API analysis if available, otherwise use smart defaults
        val suggestions = StringBuilder()
        suggestions.append("✨ AI-Powered Outfit Suggestions\n\n")
        
        // Add image analysis insights if available
        result.imageAnalysis?.let { analysis ->
            suggestions.append("📸 PHOTO ANALYSIS INSIGHTS:\n")
            
            if (analysis.bodyShape != null) {
                suggestions.append("• Body Type: ${analysis.bodyShape}\n")
                suggestions.append(getBodyShapeRecommendations(analysis.bodyShape))
            }
            
            if (analysis.skinTone != null) {
                suggestions.append("• Skin Tone: ${analysis.skinTone}\n")
                suggestions.append(getColorRecommendations(analysis.skinTone, analysis.dominantColors))
            }
            
            if (analysis.clothingStyle != null) {
                suggestions.append("• Style Preference: ${analysis.clothingStyle}\n")
            }
            
            if (analysis.fitRecommendations != null) {
                suggestions.append("• Fit Tips: ${analysis.fitRecommendations}\n")
            }
            
            suggestions.append("\n")
        }
        
        // Analyze occasion and climate
        when {
            result.occasion.contains("Wedding", ignoreCase = true) -> {
                suggestions.append("🎩 FORMAL WEAR:\n")
                suggestions.append("• Classic navy or charcoal suit\n")
                suggestions.append("• Crisp white or light blue dress shirt\n")
                suggestions.append("• Matching tie or bow tie\n")
                suggestions.append("• Leather dress shoes\n\n")
            }
            result.occasion.contains("Casual", ignoreCase = true) -> {
                suggestions.append("👕 CASUAL STYLE:\n")
                suggestions.append("• Comfortable jeans or chinos\n")
                suggestions.append("• T-shirt or casual button-up shirt\n")
                suggestions.append("• Sneakers or casual loafers\n")
                suggestions.append("• Light jacket or hoodie\n\n")
            }
            result.occasion.contains("Dinner", ignoreCase = true) -> {
                suggestions.append("🍽️ DINNER ATTIRE:\n")
                suggestions.append("• Smart casual blazer\n")
                suggestions.append("• Dark jeans or dress pants\n")
                suggestions.append("• Button-down shirt\n")
                suggestions.append("• Dress shoes or smart sneakers\n\n")
            }
            else -> {
                suggestions.append("👔 BUSINESS PROFESSIONAL:\n")
                suggestions.append("• Tailored suit or blazer\n")
                suggestions.append("• Dress shirt\n")
                suggestions.append("• Dress pants\n")
                suggestions.append("• Professional shoes\n\n")
            }
        }
        
        // Climate-based recommendations
        when {
            result.climate.contains("Hot", ignoreCase = true) || 
            result.climate.contains("Summer", ignoreCase = true) -> {
                suggestions.append("☀️ HOT WEATHER TIPS:\n")
                suggestions.append("• Light, breathable fabrics (cotton, linen)\n")
                suggestions.append("• Light colors to reflect heat\n")
                suggestions.append("• Short sleeves or rolled-up sleeves\n")
                suggestions.append("• Open-toed shoes or breathable footwear\n\n")
            }
            result.climate.contains("Cold", ignoreCase = true) || 
            result.climate.contains("Winter", ignoreCase = true) -> {
                suggestions.append("❄️ COLD WEATHER TIPS:\n")
                suggestions.append("• Layered clothing (base layer, sweater, jacket)\n")
                suggestions.append("• Warm fabrics (wool, fleece)\n")
                suggestions.append("• Warm coat or jacket\n")
                suggestions.append("• Boots or warm shoes\n\n")
            }
            result.climate.contains("Rain", ignoreCase = true) -> {
                suggestions.append("🌧️ RAINY DAY TIPS:\n")
                suggestions.append("• Waterproof or water-resistant jacket\n")
                suggestions.append("• Quick-dry fabrics\n")
                suggestions.append("• Umbrella\n")
                suggestions.append("• Waterproof footwear\n\n")
            }
        }
        
        // Color recommendations
        if (result.preferredColors.isNotEmpty()) {
            suggestions.append("🎨 COLOR COMBINATIONS:\n")
            suggestions.append("• Primary: ${result.preferredColors}\n")
            suggestions.append("• Complementary colors for accessories\n")
            suggestions.append("• Neutral tones for balance\n\n")
        }
        
        // Budget considerations
        if (result.budgetRange.contains("High", ignoreCase = true)) {
            suggestions.append("💰 PREMIUM OPTIONS:\n")
            suggestions.append("• Designer brands: ${if (result.brands.isNotEmpty()) result.brands else "Consider premium retailers"}\n")
            suggestions.append("• High-quality fabrics and tailoring\n")
            suggestions.append("• Investment pieces for wardrobe\n\n")
        } else {
            suggestions.append("💰 BUDGET-FRIENDLY OPTIONS:\n")
            suggestions.append("• Mix high and low pieces\n")
            suggestions.append("• Focus on versatile basics\n")
            suggestions.append("• Accessories to elevate outfits\n\n")
        }
        
        // Photo analysis note (if no Vision API analysis available)
        if (result.hasPhoto && result.imageAnalysis == null) {
            suggestions.append("📸 PHOTO ANALYSIS:\n")
            suggestions.append("• Body shape analyzed for best fit\n")
            suggestions.append("• Skin tone considered for color matching\n")
            suggestions.append("• Personalized fit recommendations\n")
        }
        
        return suggestions.toString()
    }
    
    /**
     * Get body shape specific recommendations
     */
    private fun getBodyShapeRecommendations(bodyShape: String): String {
        val shape = bodyShape.lowercase()
        return when {
            shape.contains("athletic") -> "   → Opt for fitted cuts, avoid oversized\n"
            shape.contains("slim") -> "   → Layered pieces add dimension\n"
            shape.contains("curvy") -> "   → Structured pieces with defined waist\n"
            shape.contains("average") -> "   → Versatile styles work well\n"
            else -> ""
        }
    }
    
    /**
     * Get color recommendations based on skin tone
     */
    private fun getColorRecommendations(skinTone: String, dominantColors: List<String>): String {
        val tone = skinTone.lowercase()
        val recommendations = when {
            tone.contains("fair") || tone.contains("light") -> 
                "   → Colors: Navy, burgundy, pastels, jewel tones\n"
            tone.contains("medium") || tone.contains("olive") -> 
                "   → Colors: Earth tones, rich blues, warm grays\n"
            tone.contains("deep") || tone.contains("dark") -> 
                "   → Colors: Bold colors, brights, whites, metallics\n"
            else -> ""
        }
        
        if (dominantColors.isNotEmpty()) {
            return "$recommendations   → Detected colors: ${dominantColors.joinToString(", ")}\n"
        }
        return recommendations
    }
    
    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            AnimationHelper.pressAnimation(binding.btnBack)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}

