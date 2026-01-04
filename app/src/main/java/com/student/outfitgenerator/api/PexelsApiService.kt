package com.student.outfitgenerator.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Simple Pexels API service
 * Free API - instant access, no approval needed!
 * Authorization header is added automatically by the interceptor
 */
interface PexelsApiService {
    
    /**
     * Search for outfit/fashion images
     * Free tier: 200 requests per hour (better than Unsplash!)
     */
    @GET("search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 1,
        @Query("orientation") orientation: String = "portrait"
    ): PexelsSearchResponse
}

