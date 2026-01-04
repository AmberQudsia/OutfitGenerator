package com.student.outfitgenerator.api

import com.google.gson.annotations.SerializedName

/**
 * Simple data models for Unsplash API
 * Free API for getting beautiful outfit images
 */
data class UnsplashSearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<UnsplashPhoto>
)

data class UnsplashPhoto(
    val id: String,
    val width: Int,
    val height: Int,
    val urls: UnsplashUrls,
    val description: String?,
    val alt_description: String?
)

data class UnsplashUrls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class UnsplashError(
    val errors: List<String>?
)

