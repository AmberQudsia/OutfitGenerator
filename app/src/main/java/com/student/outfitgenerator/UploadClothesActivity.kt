package com.student.outfitgenerator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.student.outfitgenerator.databinding.ActivityUploadClothesBinding
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class UploadClothesActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityUploadClothesBinding
    private lateinit var clothingManager: ClothingManager
    private var selectedImageUri: Uri? = null
    private var currentPhotoPath: String? = null
    
    // Camera launcher
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoPath != null) {
            selectedImageUri = Uri.fromFile(File(currentPhotoPath!!))
            displayImage(selectedImageUri!!)
        }
    }
    
    // Gallery launcher
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            displayImage(it)
        }
    }
    
    // Camera permission launcher
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        clothingManager = ClothingManager(this)
        
        setupSpinners()
        setupClickListeners()
    }
    
    private fun setupSpinners() {
        // Category spinner
        val categoryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ClothingCategory.getAll()
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = categoryAdapter
        
        // Color spinner
        val colorAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ClothingColor.getAll()
        )
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerColor.adapter = colorAdapter
    }
    
    private fun setupClickListeners() {
        binding.btnTakePhoto.setOnClickListener {
            checkCameraPermissionAndOpen()
        }
        
        binding.btnFromGallery.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        
        binding.btnSaveItem.setOnClickListener {
            saveClothingItem()
        }
    }
    
    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    
    private fun openCamera() {
        val photoFile = File(filesDir, "photo_${System.currentTimeMillis()}.jpg")
        currentPhotoPath = photoFile.absolutePath
        
        val photoUri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.fileprovider",
            photoFile
        )
        
        takePictureLauncher.launch(photoUri)
    }
    
    private fun displayImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .centerCrop()
            .into(binding.ivImagePreview)
    }
    
    private fun saveClothingItem() {
        val name = binding.etItemName.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem.toString()
        val color = binding.spinnerColor.selectedItem.toString()
        
        // Validate inputs
        if (name.isEmpty()) {
            binding.tilItemName.error = getString(R.string.error_empty_item_name)
            return
        }
        
        if (selectedImageUri == null) {
            Toast.makeText(this, getString(R.string.error_no_image), Toast.LENGTH_SHORT).show()
            return
        }
        
        // Save image to internal storage
        val savedImagePath = saveImageToInternalStorage(selectedImageUri!!)
        
        if (savedImagePath != null) {
            // Create clothing item
            val item = ClothingItem(
                id = UUID.randomUUID().toString(),
                name = name,
                category = category,
                color = color,
                imagePath = savedImagePath
            )
            
            // Save to storage
            clothingManager.addClothingItem(item)
            
            Toast.makeText(this, getString(R.string.success_item_added), Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            
            val filename = "clothing_${System.currentTimeMillis()}.jpg"
            val file = File(filesDir, filename)
            
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }
            
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

