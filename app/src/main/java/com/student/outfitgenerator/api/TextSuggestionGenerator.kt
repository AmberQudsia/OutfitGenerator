package com.student.outfitgenerator.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Simple text suggestion generator using OpenAI API
 * Generates detailed outfit suggestions text from API
 */
object TextSuggestionGenerator {
    
    /**
     * Generate outfit suggestions text from API
     */
    suspend fun generateSuggestionsText(
        occasion: String,
        climate: String,
        preferredColors: String,
        brands: String,
        budgetRange: String,
        imageAnalysis: ImageAnalysisResult? = null
    ): String? = withContext(Dispatchers.IO) {
        try {
            // Create detailed prompt for text generation
            val prompt = createSuggestionPrompt(
                occasion, climate, preferredColors, brands, budgetRange, imageAnalysis
            )
            
            // Create API request
            val request = TextGenerationRequest(
                model = "gpt-4o",
                messages = listOf(
                    TextMessage(
                        role = "system",
                        content = "You are a professional fashion stylist and personal shopper. Provide detailed, practical outfit recommendations."
                    ),
                    TextMessage(
                        role = "user",
                        content = prompt
                    )
                ),
                max_tokens = 1000,
                temperature = 0.7
            )
            
            // Call API
            val response = ApiClient.textGenerationApiService.generateText(
                authorization = "Bearer ${ApiClient.getApiKey()}",
                request = request
            )
            
            if (response.isSuccessful && response.body() != null) {
                val suggestionsText = response.body()!!.choices?.firstOrNull()?.message?.content
                return@withContext suggestionsText
            } else {
                // Log error
                val error = response.body()?.error
                println("Text Generation API Error: ${error?.message}")
                return@withContext null
            }
            
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
    
    /**
     * Create detailed prompt for text generation
     */
    private fun createSuggestionPrompt(
        occasion: String,
        climate: String,
        preferredColors: String,
        brands: String,
        budgetRange: String,
        imageAnalysis: ImageAnalysisResult?
    ): String {
        val prompt = StringBuilder()
        
        prompt.append("Generate detailed outfit suggestions for the following:\n\n")
        prompt.append("Occasion: $occasion\n")
        prompt.append("Climate/Weather: $climate\n")
        
        if (preferredColors.isNotEmpty()) {
            prompt.append("Preferred Colors: $preferredColors\n")
        }
        
        if (brands.isNotEmpty()) {
            prompt.append("Preferred Brands: $brands\n")
        }
        
        if (budgetRange != "Any Budget") {
            prompt.append("Budget Range: $budgetRange\n")
        }
        
        // Add image analysis if available
        imageAnalysis?.let { analysis ->
            prompt.append("\nPhoto Analysis:\n")
            analysis.bodyShape?.let { prompt.append("- Body Shape: $it\n") }
            analysis.skinTone?.let { prompt.append("- Skin Tone: $it\n") }
            if (analysis.dominantColors.isNotEmpty()) {
                prompt.append("- Recommended Colors: ${analysis.dominantColors.joinToString(", ")}\n")
            }
            analysis.clothingStyle?.let { prompt.append("- Style Preference: $it\n") }
            analysis.fitRecommendations?.let { prompt.append("- Fit Recommendations: $it\n") }
        }
        
        prompt.append("\n")
        prompt.append("Please provide:\n")
        prompt.append("1. Complete outfit recommendations (top, bottom, shoes, accessories)\n")
        prompt.append("2. Specific color combinations that work well\n")
        prompt.append("3. Fabric and material recommendations for the climate\n")
        prompt.append("4. Styling tips and fit advice\n")
        prompt.append("5. Budget-appropriate options if specified\n")
        prompt.append("\n")
        prompt.append("Format the response in a clear, organized way with sections and bullet points. Use emojis sparingly.")
        
        return prompt.toString()
    }
}

