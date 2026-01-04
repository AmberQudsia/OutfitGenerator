package com.student.outfitgenerator.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Simple interface for OpenAI Vision API
 * Easy to understand - just makes HTTP requests
 */
interface VisionApiService {
    
    @POST("v1/chat/completions")
    suspend fun analyzeImage(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body request: VisionRequest
    ): Response<VisionResponse>
}

