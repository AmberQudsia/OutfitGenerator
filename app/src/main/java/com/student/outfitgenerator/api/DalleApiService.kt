package com.student.outfitgenerator.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Interface for DALL-E Image Generation API
 * Endpoint: POST https://api.openai.com/v1/images/generations
 */
interface DalleApiService {
    
    @POST("v1/images/generations")
    suspend fun generateImage(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body request: DalleImageRequest
    ): Response<DalleImageResponse>
}

