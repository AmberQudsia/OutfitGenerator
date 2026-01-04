package com.student.outfitgenerator.api

import com.google.gson.annotations.SerializedName

/**
 * Models for OpenAI Text Generation API (Chat Completions)
 * For generating outfit suggestions text
 */

// Request Model
data class TextGenerationRequest(
    val model: String = "gpt-4o",
    val messages: List<TextMessage>,
    val max_tokens: Int = 1000,
    val temperature: Double = 0.7
)

data class TextMessage(
    val role: String,
    val content: String
)

// Response Model
data class TextGenerationResponse(
    val choices: List<TextChoice>?,
    val error: TextError?
)

data class TextChoice(
    val message: TextResponseMessage
)

data class TextResponseMessage(
    val content: String
)

data class TextError(
    val message: String,
    val type: String?,
    val code: String?
)

