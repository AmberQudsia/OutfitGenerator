package com.student.outfitgenerator.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Simple Unsplash API service
 * Free API - no payment required!
 * Authorization header is added automatically by the interceptor
 */
interface UnsplashApiService {
    
    /**
     * Search for outfit/fashion images
     * Free tier: 50 requests per hour
     */
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 1,
        @Query("orientation") orientation: String = "portrait"
    ): UnsplashSearchResponse
}

