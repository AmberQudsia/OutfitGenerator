package com.student.outfitgenerator

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ClothingManager(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("ClothingPrefs", Context.MODE_PRIVATE)
    
    private val gson = Gson()
    
    companion object {
        private const val KEY_CLOTHING_ITEMS = "clothing_items"
    }
    
    // Save a clothing item
    fun addClothingItem(item: ClothingItem) {
        val items = getAllClothingItems().toMutableList()
        items.add(item)
        saveClothingItems(items)
    }
    
    // Get all clothing items
    fun getAllClothingItems(): List<ClothingItem> {
        val json = sharedPreferences.getString(KEY_CLOTHING_ITEMS, null) ?: return emptyList()
        val type = object : TypeToken<List<ClothingItem>>() {}.type
        return gson.fromJson(json, type)
    }
    
    // Get items by category
    fun getItemsByCategory(category: String): List<ClothingItem> {
        return getAllClothingItems().filter { it.category == category }
    }
    
    // Get items by color
    fun getItemsByColor(color: String): List<ClothingItem> {
        return getAllClothingItems().filter { it.color == color }
    }
    
    // Delete an item
    fun deleteClothingItem(id: String) {
        val items = getAllClothingItems().filter { it.id != id }
        saveClothingItems(items)
    }
    
    // Save all items
    private fun saveClothingItems(items: List<ClothingItem>) {
        val json = gson.toJson(items)
        sharedPreferences.edit().putString(KEY_CLOTHING_ITEMS, json).apply()
    }
    
    // Clear all items
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
    
    // Generate outfit suggestions
    fun generateOutfitSuggestions(): List<OutfitSuggestion> {
        val tops = getItemsByCategory(ClothingCategory.TOP)
        val bottoms = getItemsByCategory(ClothingCategory.BOTTOM)
        val shoes = getItemsByCategory(ClothingCategory.SHOES)
        
        if (tops.isEmpty() || bottoms.isEmpty()) {
            return emptyList()
        }
        
        val suggestions = mutableListOf<OutfitSuggestion>()
        
        // Generate color-matched outfits
        for (top in tops) {
            val matchingColors = ClothingColor.getMatchingColors(top.color)
            
            for (bottom in bottoms) {
                // Check if colors match or if bottom is neutral
                val isColorMatch = matchingColors.contains(bottom.color)
                val isNeutralBottom = bottom.color in listOf(ClothingColor.BLACK, ClothingColor.WHITE, ClothingColor.GRAY)
                
                if (isColorMatch || isNeutralBottom) {
                    // Find best matching shoes
                    val matchingShoes = findBestShoes(top.color, bottom.color, shoes)
                    
                    suggestions.add(
                        OutfitSuggestion(
                            top = top,
                            bottom = bottom,
                            shoes = matchingShoes,
                            matchScore = calculateMatchScore(top, bottom, matchingShoes)
                        )
                    )
                }
            }
        }
        
        // If no color matches found, create basic combinations
        if (suggestions.isEmpty()) {
            for (top in tops) {
                for (bottom in bottoms) {
                    val matchingShoes = findBestShoes(top.color, bottom.color, shoes)
                    suggestions.add(
                        OutfitSuggestion(
                            top = top,
                            bottom = bottom,
                            shoes = matchingShoes,
                            matchScore = calculateMatchScore(top, bottom, matchingShoes)
                        )
                    )
                }
            }
        }
        
        // Sort by match score and return top 10
        return suggestions.sortedByDescending { it.matchScore }.take(10)
    }
    
    // Find best matching shoes for an outfit
    private fun findBestShoes(topColor: String, bottomColor: String, shoes: List<ClothingItem>): ClothingItem? {
        if (shoes.isEmpty()) return null
        
        // Priority order for shoe colors
        val shoePriority = listOf(
            ClothingColor.BLACK,
            ClothingColor.WHITE,
            ClothingColor.BROWN,
            ClothingColor.GRAY,
            ClothingColor.NAVY
        )
        
        // Try to find shoes in priority order
        for (color in shoePriority) {
            val matchingShoes = shoes.filter { it.color == color }
            if (matchingShoes.isNotEmpty()) {
                return matchingShoes.first()
            }
        }
        
        // If no priority colors found, return first available shoe
        return shoes.first()
    }
    
    private fun calculateMatchScore(top: ClothingItem, bottom: ClothingItem, shoes: ClothingItem?): Int {
        var score = 40 // Base score
        
        // Color matching bonus
        val matchingColors = ClothingColor.getMatchingColors(top.color)
        if (matchingColors.contains(bottom.color)) {
            score += 35 // High bonus for color match
        }
        
        // Neutral color bonus
        if (bottom.color in listOf(ClothingColor.BLACK, ClothingColor.WHITE, ClothingColor.GRAY)) {
            score += 25 // Good bonus for neutral bottoms
        }
        
        // Same color bonus (monochrome)
        if (top.color == bottom.color) {
            score += 15 // Bonus for monochrome looks
        }
        
        // Shoes matching bonus
        if (shoes != null) {
            score += 15 // Good bonus for having shoes
            
            // Shoe color matching
            if (shoes.color in listOf(ClothingColor.BLACK, ClothingColor.WHITE, ClothingColor.BROWN)) {
                score += 10 // Extra bonus for versatile shoe colors
            }
        }
        
        // Perfect color combination bonus
        if (isPerfectColorMatch(top.color, bottom.color)) {
            score += 20 // Extra bonus for perfect matches
        }
        
        return score.coerceIn(0, 100)
    }
    
    // Check for perfect color combinations
    private fun isPerfectColorMatch(topColor: String, bottomColor: String): Boolean {
        val perfectMatches = listOf(
            Pair(ClothingColor.BLUE, ClothingColor.WHITE),
            Pair(ClothingColor.WHITE, ClothingColor.BLUE),
            Pair(ClothingColor.BLACK, ClothingColor.WHITE),
            Pair(ClothingColor.WHITE, ClothingColor.BLACK),
            Pair(ClothingColor.RED, ClothingColor.BLACK),
            Pair(ClothingColor.BLACK, ClothingColor.RED),
            Pair(ClothingColor.GREEN, ClothingColor.BROWN),
            Pair(ClothingColor.BROWN, ClothingColor.GREEN),
            Pair(ClothingColor.PINK, ClothingColor.WHITE),
            Pair(ClothingColor.WHITE, ClothingColor.PINK)
        )
        
        return perfectMatches.contains(Pair(topColor, bottomColor))
    }
}

data class OutfitSuggestion(
    val top: ClothingItem,
    val bottom: ClothingItem,
    val shoes: ClothingItem?,
    val matchScore: Int
)

