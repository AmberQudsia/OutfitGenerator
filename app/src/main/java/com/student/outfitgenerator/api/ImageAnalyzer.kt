package com.student.outfitgenerator.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * Simple image analyzer using Vision API
 * Easy to understand - converts image to base64 and sends to API
 */
object ImageAnalyzer {
    
    /**
     * Analyze an image using Vision API
     * Returns analysis result with body shape, skin tone, colors, etc.
     */
    suspend fun analyzeImage(
        context: Context,
        imageUri: Uri,
        occasion: String,
        climate: String
    ): ImageAnalysisResult? = withContext(Dispatchers.IO) {
        try {
            // Convert image to base64
            val base64Image = imageUriToBase64(context, imageUri) ?: return@withContext null
            
            // Create prompt for Vision API
            val prompt = createAnalysisPrompt(occasion, climate)
            
            // Create API request
            val request = VisionRequest(
                model = "gpt-4o", // or "gpt-4-vision-preview"
                messages = listOf(
                    VisionMessage(
                        role = "user",
                        content = listOf(
                            VisionContent(
                                type = "text",
                                text = prompt
                            ),
                            VisionContent(
                                type = "image_url",
                                image_url = ImageUrl(url = "data:image/jpeg;base64,$base64Image")
                            )
                        )
                    )
                ),
                max_tokens = 500
            )
            
            // Call API
            val response = ApiClient.visionApiService.analyzeImage(
                authorization = "Bearer ${ApiClient.getApiKey()}",
                request = request
            )
            
            if (response.isSuccessful && response.body() != null) {
                val analysisText = response.body()!!.choices.firstOrNull()?.message?.content ?: ""
                return@withContext parseAnalysisResult(analysisText)
            } else {
                // API call failed - return null (will use mock in this case)
                return@withContext null
            }
            
        } catch (e: Exception) {
            e.printStackTrace()
            // If API fails, return null (will use mock suggestions)
            return@withContext null
        }
    }
    
    /**
     * Convert image URI to base64 string
     */
    private fun imageUriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            // Resize if too large (to reduce API costs)
            val resizedBitmap = resizeBitmap(bitmap, maxDimension = 1024)
            
            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
            val imageBytes = outputStream.toByteArray()
            
            Base64.encodeToString(imageBytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Resize bitmap to reduce size
     */
    private fun resizeBitmap(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        
        if (width <= maxDimension && height <= maxDimension) {
            return bitmap
        }
        
        val scale = maxDimension.toFloat() / maxOf(width, height)
        val newWidth = (width * scale).toInt()
        val newHeight = (height * scale).toInt()
        
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }
    
    /**
     * Create prompt for Vision API
     */
    private fun createAnalysisPrompt(occasion: String, climate: String): String {
        return """
            Analyze this person's photo for outfit recommendations. Provide:
            
            1. Body shape analysis (e.g., athletic, slim, average, curvy)
            2. Skin tone description (e.g., fair, medium, olive, deep)
            3. Dominant colors in the photo that would complement their appearance
            4. Clothing style preferences visible (if any)
            5. Fit recommendations based on body type
            
            Context:
            - Occasion: $occasion
            - Climate: $climate
            
            Provide a concise analysis in a structured format.
        """.trimIndent()
    }
    
    /**
     * Parse API response into structured result
     */
    private fun parseAnalysisResult(analysisText: String): ImageAnalysisResult {
        // Simple parsing - extract key information
        // In production, you might want more sophisticated parsing
        
        val bodyShape = extractValue(analysisText, listOf("body shape", "body type"))
        val skinTone = extractValue(analysisText, listOf("skin tone", "complexion"))
        val colors = extractColors(analysisText)
        val clothingStyle = extractValue(analysisText, listOf("style", "fashion"))
        val fitRecommendations = extractValue(analysisText, listOf("fit", "sizing"))
        
        return ImageAnalysisResult(
            bodyShape = bodyShape,
            skinTone = skinTone,
            dominantColors = colors,
            clothingStyle = clothingStyle,
            fitRecommendations = fitRecommendations,
            analysisText = analysisText
        )
    }
    
    private fun extractValue(text: String, keywords: List<String>): String? {
        val lowerText = text.lowercase()
        for (keyword in keywords) {
            val index = lowerText.indexOf(keyword)
            if (index != -1) {
                // Extract text after keyword (simple approach)
                val start = index + keyword.length
                val end = minOf(start + 50, text.length)
                return text.substring(start, end).trim().take(100)
            }
        }
        return null
    }
    
    private fun extractColors(text: String): List<String> {
        val colorKeywords = listOf(
            "blue", "red", "green", "yellow", "orange", "purple",
            "pink", "brown", "black", "white", "gray", "navy",
            "beige", "maroon", "teal", "burgundy"
        )
        val foundColors = mutableListOf<String>()
        val lowerText = text.lowercase()
        
        for (color in colorKeywords) {
            if (lowerText.contains(color)) {
                foundColors.add(color)
            }
        }
        
        return foundColors.distinct()
    }
}

