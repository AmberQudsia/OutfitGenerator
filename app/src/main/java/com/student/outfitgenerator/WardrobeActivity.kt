package com.student.outfitgenerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.student.outfitgenerator.databinding.ActivityWardrobeBinding
import com.student.outfitgenerator.databinding.ItemClothingBinding
import java.io.File

class WardrobeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityWardrobeBinding
    private lateinit var clothingManager: ClothingManager
    private lateinit var adapter: WardrobeAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWardrobeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        clothingManager = ClothingManager(this)
        
        setupRecyclerView()
        loadClothingItems()
    }
    
    private fun setupRecyclerView() {
        adapter = WardrobeAdapter(
            onDeleteClick = { item -> showDeleteDialog(item) }
        )
        
        binding.rvWardrobe.layoutManager = GridLayoutManager(this, 2)
        binding.rvWardrobe.adapter = adapter
    }
    
    private fun loadClothingItems() {
        val items = clothingManager.getAllClothingItems()
        
        if (items.isEmpty()) {
            binding.tvEmptyMessage.visibility = View.VISIBLE
            binding.rvWardrobe.visibility = View.GONE
        } else {
            binding.tvEmptyMessage.visibility = View.GONE
            binding.rvWardrobe.visibility = View.VISIBLE
            adapter.submitList(items)
        }
    }
    
    private fun showDeleteDialog(item: ClothingItem) {
        AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete ${item.name}?")
            .setPositiveButton("Delete") { _, _ ->
                deleteItem(item)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteItem(item: ClothingItem) {
        // Delete image file
        val file = File(item.imagePath)
        if (file.exists()) {
            file.delete()
        }
        
        // Delete from storage
        clothingManager.deleteClothingItem(item.id)
        
        Toast.makeText(this, getString(R.string.item_deleted), Toast.LENGTH_SHORT).show()
        
        // Reload items
        loadClothingItems()
    }
    
    override fun onResume() {
        super.onResume()
        loadClothingItems()
    }
}

class WardrobeAdapter(
    private val onDeleteClick: (ClothingItem) -> Unit
) : RecyclerView.Adapter<WardrobeAdapter.ViewHolder>() {
    
    private var items: List<ClothingItem> = emptyList()
    
    fun submitList(newItems: List<ClothingItem>) {
        items = newItems
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClothingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount() = items.size
    
    inner class ViewHolder(
        private val binding: ItemClothingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: ClothingItem) {
            binding.tvItemName.text = item.name
            binding.tvItemCategory.text = item.category
            binding.tvItemColor.text = item.color
            
            // Load image
            Glide.with(binding.root.context)
                .load(File(item.imagePath))
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.ivClothingItem)
            
            binding.btnDelete.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }
}





