package com.rysanek.testimplicitintents.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rysanek.testimplicitintents.R
import com.rysanek.testimplicitintents.databinding.FragmentGalleryIntentBinding

/**
 * This [Fragment] let's the user invoke an *implicit* [Intent] using
 * [registerForActivityResult] with [ActivityResultContracts.StartActivityForResult]
 * to take the user to the picture gallery and allow then to select an existing image.
 * **/
class GalleryIntentFragment: BaseFragment() {
    companion object { const val TAG = "GalleryIntentFragment" }
    private var _binding: FragmentGalleryIntentBinding? = null
    private val binding get() = _binding!!
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Register the contract. It need to be registered before you can use it.
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    
                    // The activity result returns a URI for the location of the file.
                    activityResult.data?.data?.let { uri ->
                        
                        // Load Image using URI
                        Glide.with(requireContext()).load(uri).into(binding.ivPicResult)
                    }
                }
            }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryIntentBinding.inflate(inflater, container, false)
        
        binding.btGoToGallery.setOnClickListener {
            // Intent to pick items in the Images folder
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also { intent ->
                
                // checking whether or not any application can handle the implicit request
                intent.resolveActivity(requireActivity().packageManager)?.also {
                    Log.d(TAG, "There are applications to handle implicit intent.")
                    
                    // Launch the activity for result contract passing the implicit intent
                    galleryLauncher.launch(intent)
                    
                } ?: run {
                    Log.d(TAG, "There are NO applications to handle implicit intent.")
    
                    // Letting the user know that the action cannot be handled by any application.
                    showCantHandleMessage()
                }
            }
        }
        
        return binding.root
    }
    
    // Show a SnackBar to the user informing No application can handle request
    private fun showCantHandleMessage() {
        Snackbar.make(
            requireContext(),
            binding.root,
            "No application can perform the desired action.",
            Snackbar.LENGTH_SHORT
        ).show()
    }
    
}