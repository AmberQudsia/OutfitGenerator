package com.student.outfitgenerator.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Simple API client setup
 * Creates the Retrofit service for making API calls
 */
object ApiClient {
    
    // OpenAI API (for text and vision analysis)
    private const val OPENAI_BASE_URL = "https://api.openai.com/"
    
    // Pexels API (free for outfit images - instant access, no approval!)
    private const val PEXELS_BASE_URL = "https://api.pexels.com/v1/"
    
    // OpenAI API key (for text suggestions and vision)
    private const val OPENAI_API_KEY = ""
    
    // Pexels API Key (free - instant access, no approval needed!)
    // Get it at: https://www.pexels.com/api/
    private const val PEXELS_API_KEY = "ARVSK60iArVfFbgUVSiwCXFDpwDSuZ5MYN9k6Em2gE17SXVsoulWRvxv"
    
    init {
        println("API Client initialized")
        if (PEXELS_API_KEY == "YOUR_PEXELS_API_KEY_HERE" || PEXELS_API_KEY.isEmpty()) {
            println("⚠️ WARNING: Please add your Pexels API Key!")
            println("   Get free key (instant): https://www.pexels.com/api/")
            println("   Then update PEXELS_API_KEY in ApiClient.kt")
        } else {
            println("✅ Pexels API Key configured (length: ${PEXELS_API_KEY.length})")
        }
    }
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    // OpenAI client (adds Bearer token)
    private val openAiOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $OPENAI_API_KEY")
                .addHeader("Content-Type", "application/json")
            
            if (originalRequest.body != null) {
                requestBuilder.method(originalRequest.method, originalRequest.body)
            } else {
                requestBuilder.method(originalRequest.method, null)
            }
            
            chain.proceed(requestBuilder.build())
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    
    // Pexels client (adds Authorization header)
    private val pexelsOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .addHeader("Authorization", PEXELS_API_KEY)
            
            chain.proceed(requestBuilder.build())
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    
    // OpenAI Retrofit instance
    private val openAiRetrofit = Retrofit.Builder()
        .baseUrl(OPENAI_BASE_URL)
        .client(openAiOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    // Pexels Retrofit instance
    private val pexelsRetrofit = Retrofit.Builder()
        .baseUrl(PEXELS_BASE_URL)
        .client(pexelsOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    // OpenAI services
    val visionApiService: VisionApiService = openAiRetrofit.create(VisionApiService::class.java)
    val textGenerationApiService: TextGenerationApiService = openAiRetrofit.create(TextGenerationApiService::class.java)
    
    // Pexels service (free - instant access!)
    val pexelsApiService: PexelsApiService = pexelsRetrofit.create(PexelsApiService::class.java)
    
    /**
     * Get OpenAI API key - in production, read from secure storage
     */
    fun getApiKey(): String {
        return OPENAI_API_KEY
    }
    
    /**
     * Get Pexels API Key
     */
    fun getPexelsApiKey(): String {
        return PEXELS_API_KEY
    }
}

