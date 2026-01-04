package com.student.outfitgenerator.api

import com.google.gson.annotations.SerializedName

/**
 * Models for OpenAI Vision API
 * Simple data classes for API requests and responses
 */

// Request Models
data class VisionRequest(
    val model: String = "gpt-4-vision-preview",
    val messages: List<VisionMessage>,
    val max_tokens: Int = 500
)

data class VisionMessage(
    val role: String = "user",
    val content: List<VisionContent>
)

data class VisionContent(
    val type: String,
    val text: String? = null,
    val image_url: ImageUrl? = null
)

data class ImageUrl(
    val url: String  // Base64 encoded image or URL
)

// Response Models
data class VisionResponse(
    val choices: List<VisionChoice>
)

data class VisionChoice(
    val message: VisionResponseMessage
)

data class VisionResponseMessage(
    val content: String
)

/**
 * Parsed analysis result from Vision API
 */
data class ImageAnalysisResult(
    val bodyShape: String? = null,
    val skinTone: String? = null,
    val dominantColors: List<String> = emptyList(),
    val clothingStyle: String? = null,
    val fitRecommendations: String? = null,
    val analysisText: String = ""
) : java.io.Serializable

