package com.student.outfitgenerator.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Simple image generator using Pexels API (FREE!)
 * Searches for beautiful outfit images based on user preferences
 * Instant access - no approval needed!
 */
object ImageGenerator {
    
    /**
     * Get outfit image from Pexels based on suggestions
     * This is FREE - no payment required, instant access!
     */
    suspend fun generateOutfitImage(
        occasion: String,
        climate: String,
        preferredColors: String,
        imageAnalysis: ImageAnalysisResult? = null
    ): String? = withContext(Dispatchers.IO) {
        try {
            println("Pexels API: Starting image search...")
            println("Pexels API: Occasion=$occasion, Climate=$climate, Colors=$preferredColors")
            
            // Create search query based on user preferences
            val searchQuery = createSearchQuery(occasion, climate, preferredColors, imageAnalysis)
            println("Pexels API: Search query: '$searchQuery'")
            
            val apiKey = ApiClient.getPexelsApiKey()
            
            // Check if API key is set
            if (apiKey == "YOUR_PEXELS_API_KEY_HERE" || apiKey.isEmpty()) {
                println("Pexels API: ⚠️ API key not set!")
                println("Pexels API: Get free key (instant) at: https://www.pexels.com/api/")
                return@withContext null
            }
            
            // Search for outfit images
            println("Pexels API: Making API call...")
            println("Pexels API: Query='$searchQuery', perPage=1, orientation=portrait")
            
            val response = ApiClient.pexelsApiService.searchPhotos(
                query = searchQuery,
                perPage = 1,
                orientation = "portrait"
            )
            
            println("Pexels API: Response received!")
            println("Pexels API: Total results: ${response.total_results}")
            println("Pexels API: Photos array size: ${response.photos.size}")
            println("Pexels API: Page: ${response.page}")
            
            // Get the first result
            val photo = response.photos.firstOrNull()
            
            if (photo != null) {
                // Use large size (good quality)
                val imageUrl = photo.src.large
                println("Pexels API: ✅ Image found!")
                println("Pexels API: Image URL: $imageUrl")
                println("Pexels API: Photo ID: ${photo.id}")
                return@withContext imageUrl
            } else {
                println("Pexels API: ❌ No photos in results array")
                println("Pexels API: Total results was: ${response.total_results}")
                println("Pexels API: Try a different search query")
                return@withContext null
            }
            
        } catch (e: Exception) {
            println("Pexels API Exception: ${e.message}")
            println("Pexels API Exception Type: ${e.javaClass.simpleName}")
            e.printStackTrace()
            
            // Log more details if it's a network/HTTP error
            if (e.message?.contains("401", ignoreCase = true) == true) {
                println("Pexels API: 401 Unauthorized - Check API key!")
            } else if (e.message?.contains("403", ignoreCase = true) == true) {
                println("Pexels API: 403 Forbidden - API key may be invalid")
            } else if (e.message?.contains("429", ignoreCase = true) == true) {
                println("Pexels API: 429 Rate Limit - Too many requests")
            }
            
            // If Pexels fails, return null (text suggestions will still work)
            return@withContext null
        }
    }
    
    /**
     * Create search query for Pexels based on user preferences
     * Keeps it simple and search-friendly
     */
    private fun createSearchQuery(
        occasion: String,
        climate: String,
        preferredColors: String,
        imageAnalysis: ImageAnalysisResult?
    ): String {
        val queryParts = mutableListOf<String>()
        
        // Add occasion-based terms
        when {
            occasion.contains("Wedding", ignoreCase = true) -> queryParts.add("wedding outfit formal dress")
            occasion.contains("Casual", ignoreCase = true) -> queryParts.add("casual outfit fashion")
            occasion.contains("Dinner", ignoreCase = true) -> queryParts.add("dinner outfit elegant fashion")
            occasion.contains("Business", ignoreCase = true) -> queryParts.add("business professional outfit")
            occasion.contains("Party", ignoreCase = true) -> queryParts.add("party outfit fashion")
            occasion.contains("Date", ignoreCase = true) -> queryParts.add("date night outfit fashion")
            else -> queryParts.add("outfit fashion")
        }
        
        // Add climate-based terms
        when {
            climate.contains("Cold", ignoreCase = true) || climate.contains("Winter", ignoreCase = true) -> {
                queryParts.add("winter")
            }
            climate.contains("Hot", ignoreCase = true) || climate.contains("Summer", ignoreCase = true) -> {
                queryParts.add("summer")
            }
            climate.contains("Rain", ignoreCase = true) -> {
                queryParts.add("rainy weather")
            }
        }
        
        // Add color preferences if provided
        if (preferredColors.isNotEmpty()) {
            // Take first color mentioned
            val firstColor = preferredColors.split(",").firstOrNull()?.trim()
            if (firstColor != null && firstColor.length < 20) {
                queryParts.add(firstColor)
            }
        }
        
        // Combine into search query
        val query = queryParts.joinToString(" ")
        
        println("Pexels API: Created search query: '$query'")
        return query
    }
}
