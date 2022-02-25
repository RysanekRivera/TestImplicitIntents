package com.rysanek.testimplicitintents.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rysanek.testimplicitintents.R
import com.rysanek.testimplicitintents.adapters.IntroAdapter
import com.rysanek.testimplicitintents.databinding.FragmentIntroBinding
import com.rysanek.testimplicitintents.models.RvItem

class IntroFragment: Fragment() {
    
    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!
    private lateinit var introAdapter: IntroAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        
        setupRecyclerView()
        
        val listOfItems = mutableListOf(
            RvItem(R.drawable.ic_camera, getString(R.string.take_a_picture), CameraIntentFragment::class),
            RvItem(R.drawable.ic_photo_gallery, getString(R.string.go_to_gallery), GalleryIntentFragment::class)
        )
        
        introAdapter.setData(listOfItems)
        
        return binding.root
    }
    
    private fun setupRecyclerView(){
        introAdapter = IntroAdapter {
            when(this){
                CameraIntentFragment::class -> { findNavController().navigate(R.id.action_introFragment_to_cameraIntentFragment) }
                GalleryIntentFragment::class -> { findNavController().navigate(R.id.action_introFragment_to_galleryIntentFragment) }
            }
        }
        
        binding.rvIntro.apply {
            adapter = introAdapter
            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}