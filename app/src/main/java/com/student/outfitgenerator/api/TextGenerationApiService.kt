package com.student.outfitgenerator.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Interface for OpenAI Text Generation API (Chat Completions)
 */
interface TextGenerationApiService {
    
    @POST("v1/chat/completions")
    suspend fun generateText(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body request: TextGenerationRequest
    ): Response<TextGenerationResponse>
}

