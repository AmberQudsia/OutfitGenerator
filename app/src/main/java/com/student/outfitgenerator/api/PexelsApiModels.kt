package com.student.outfitgenerator.api

/**
 * Simple data models for Pexels API
 * Free API - no approval needed, instant access!
 */
data class PexelsSearchResponse(
    val total_results: Int,
    val page: Int,
    val per_page: Int,
    val photos: List<PexelsPhoto>,
    val next_page: String?
)

data class PexelsPhoto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val src: PexelsPhotoSrc,
    val alt: String?
)

data class PexelsPhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

