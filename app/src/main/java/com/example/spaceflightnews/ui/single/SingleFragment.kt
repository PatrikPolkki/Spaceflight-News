package com.example.spaceflightnews.ui.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spaceflightnews.databinding.FragmentSingleBinding

class SingleFragment : Fragment() {

    private lateinit var binding: FragmentSingleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleBinding.inflate(layoutInflater)
        return binding.root
    }
}