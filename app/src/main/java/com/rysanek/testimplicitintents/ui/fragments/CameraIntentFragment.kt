package com.rysanek.testimplicitintents.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rysanek.testimplicitintents.R
import com.rysanek.testimplicitintents.databinding.FragmentCameraIntentBinding
import java.io.File
/**
 * This [Fragment] let's the user invoke an *implicit* [Intent] using
 * [registerForActivityResult] with [ActivityResultContracts.TakePicture].
 *
 * The user will get a temporary file containing the picture taken and
 * it will be loaded into the ImageView.
 * **/
class CameraIntentFragment: BaseFragment() {
    companion object { const val TAG = "CameraIntentFragment" }
    private var _binding: FragmentCameraIntentBinding? = null
    private val binding get() = _binding!!
    private lateinit var uri: Uri
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Register the contract. It needs to be registered before you can use it.
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            
            // If the picture is saved in the uri that was passed then we will load it.
            if (isSaved) binding.ivPicResult.setImageURI(uri)
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraIntentBinding.inflate(inflater, container, false)
        
        binding.btTakePic.setOnClickListener {
            
            // Create temporary file that will hold the picture
            val pictureFile = File.createTempFile(
                "PIC_",
                ".jpg",
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )
            
            Log.d(TAG, "Filename: $pictureFile")
            
            // Create the URI where the temporary file will be stored
            uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                pictureFile
            )
    
            Log.d(TAG, "Uri: $uri")
    
            // Launching the camera intent. It requires the URI of the file to save the pic.
            cameraLauncher.launch(uri)
        }
        
        return binding.root
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}