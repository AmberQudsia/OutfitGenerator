package com.student.outfitgenerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.student.outfitgenerator.databinding.ActivitySuggestionsBinding
import com.student.outfitgenerator.databinding.ItemSuggestionBinding
import java.io.File

class SuggestionsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySuggestionsBinding
    private lateinit var clothingManager: ClothingManager
    private lateinit var adapter: SuggestionsAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        clothingManager = ClothingManager(this)
        
        setupRecyclerView()
        loadSuggestions()
    }
    
    private fun setupRecyclerView() {
        adapter = SuggestionsAdapter()
        
        binding.rvSuggestions.layoutManager = LinearLayoutManager(this)
        binding.rvSuggestions.adapter = adapter
    }
    
    private fun loadSuggestions() {
        val allItems = clothingManager.getAllClothingItems()
        val tops = clothingManager.getItemsByCategory(ClothingCategory.TOP)
        val bottoms = clothingManager.getItemsByCategory(ClothingCategory.BOTTOM)
        val shoes = clothingManager.getItemsByCategory(ClothingCategory.SHOES)
        
        // Debug logging
        android.util.Log.d("SuggestionsActivity", "Total items: ${allItems.size}")
        android.util.Log.d("SuggestionsActivity", "Tops: ${tops.size}, Bottoms: ${bottoms.size}, Shoes: ${shoes.size}")
        
        val suggestions = clothingManager.generateOutfitSuggestions()
        android.util.Log.d("SuggestionsActivity", "Generated suggestions: ${suggestions.size}")
        
        if (suggestions.isEmpty()) {
            binding.tvEmptyMessage.visibility = View.VISIBLE
            binding.rvSuggestions.visibility = View.GONE
            
            // Check if user has items
            if (allItems.isNotEmpty()) {
                if (tops.isEmpty() || bottoms.isEmpty()) {
                    binding.tvEmptyMessage.text = getString(R.string.need_more_items)
                } else {
                    binding.tvEmptyMessage.text = "No matching outfits found. Try adding more items with different colors."
                }
            }
        } else {
            binding.tvEmptyMessage.visibility = View.GONE
            binding.rvSuggestions.visibility = View.VISIBLE
            adapter.submitList(suggestions)
        }
    }
}

class SuggestionsAdapter : RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {
    
    private var suggestions: List<OutfitSuggestion> = emptyList()
    
    fun submitList(newSuggestions: List<OutfitSuggestion>) {
        suggestions = newSuggestions
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSuggestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(suggestions[position])
    }
    
    override fun getItemCount() = suggestions.size
    
    class ViewHolder(
        private val binding: ItemSuggestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(suggestion: OutfitSuggestion) {
            val context = binding.root.context
            
            // Match score
            binding.tvMatchScore.text = context.getString(R.string.match_score, suggestion.matchScore)
            
            // Top item
            binding.tvTopName.text = suggestion.top.name
            binding.tvTopColor.text = suggestion.top.color
            Glide.with(context)
                .load(File(suggestion.top.imagePath))
                .centerCrop()
                .into(binding.ivTop)
            
            // Bottom item
            binding.tvBottomName.text = suggestion.bottom.name
            binding.tvBottomColor.text = suggestion.bottom.color
            Glide.with(context)
                .load(File(suggestion.bottom.imagePath))
                .centerCrop()
                .into(binding.ivBottom)
            
            // Shoes item (optional)
            if (suggestion.shoes != null) {
                binding.cardShoes.visibility = View.VISIBLE
                binding.tvShoesLabel.visibility = View.VISIBLE
                binding.tvShoesName.text = suggestion.shoes.name
                binding.tvShoesColor.text = suggestion.shoes.color
                Glide.with(context)
                    .load(File(suggestion.shoes.imagePath))
                    .centerCrop()
                    .into(binding.ivShoes)
            } else {
                binding.cardShoes.visibility = View.GONE
                binding.tvShoesLabel.visibility = View.GONE
            }
        }
    }
}

