package com.student.outfitgenerator.api

import com.google.gson.annotations.SerializedName

/**
 * Models for DALL-E Image Generation API
 * Simple data classes for generating outfit images
 */

// Request Model
data class DalleImageRequest(
    val model: String = "dall-e-3",
    val prompt: String,
    val n: Int = 1,
    val size: String = "1024x1024",
    val quality: String = "standard",
    val style: String = "natural"
)

// Response Model
data class DalleImageResponse(
    val data: List<DalleImageData>?,
    val error: DalleError?
)

data class DalleImageData(
    val url: String?,
    @SerializedName("revised_prompt")
    val revisedPrompt: String?
)

data class DalleError(
    val message: String,
    val type: String?,
    val code: String?
)

