package com.student.outfitgenerator

import java.io.Serializable

data class ClothingItem(
    val id: String,
    val name: String,
    val category: String, // "Top", "Bottom", "Shoes", "Accessory"
    val color: String, // Main color of the item
    val imagePath: String,
    val dateAdded: Long = System.currentTimeMillis()
) : Serializable

object ClothingCategory {
    const val TOP = "Top"
    const val BOTTOM = "Bottom"
    const val SHOES = "Shoes"
    const val ACCESSORY = "Accessory"
    
    fun getAll() = listOf(TOP, BOTTOM, SHOES, ACCESSORY)
}

object ClothingColor {
    const val RED = "Red"
    const val BLUE = "Blue"
    const val GREEN = "Green"
    const val YELLOW = "Yellow"
    const val BLACK = "Black"
    const val WHITE = "White"
    const val GRAY = "Gray"
    const val BROWN = "Brown"
    const val PINK = "Pink"
    const val PURPLE = "Purple"
    const val ORANGE = "Orange"
    const val NAVY = "Navy"
    const val BEIGE = "Beige"
    
    fun getAll() = listOf(RED, BLUE, GREEN, YELLOW, BLACK, WHITE, GRAY, BROWN, PINK, PURPLE, ORANGE, NAVY, BEIGE)
    
    // Simple color matching algorithm
    fun getMatchingColors(color: String): List<String> {
        return when (color) {
            BLACK, WHITE, GRAY -> getAll() // Neutral colors match everything
            BLUE -> listOf(WHITE, GRAY, BLACK, BROWN, BEIGE, NAVY)
            RED -> listOf(BLACK, WHITE, GRAY, NAVY, BLUE)
            GREEN -> listOf(BROWN, BLACK, WHITE, BEIGE, GRAY)
            YELLOW -> listOf(GRAY, BLACK, BLUE, WHITE, BROWN)
            PINK -> listOf(WHITE, GRAY, BLACK, NAVY, PURPLE)
            PURPLE -> listOf(BLACK, WHITE, GRAY, PINK, BLUE)
            ORANGE -> listOf(BROWN, BLACK, WHITE, NAVY, YELLOW)
            BROWN -> listOf(BEIGE, WHITE, BLACK, GREEN, GRAY)
            NAVY -> listOf(WHITE, GRAY, BLACK, BLUE, RED)
            BEIGE -> listOf(BROWN, WHITE, BLACK, GRAY, GREEN)
            else -> listOf(BLACK, WHITE, GRAY) // Default fallback
        }
    }
}

