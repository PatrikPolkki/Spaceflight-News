package com.example.spaceflightnews.ui.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.databinding.FragmentSingleBinding

class SingleFragment : Fragment() {

    private val args: SingleFragmentArgs by navArgs()
    private lateinit var binding: FragmentSingleBinding
    private val viewModel: SingleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleBinding.inflate(layoutInflater)
        binding.apply {
            singleViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        viewModel.addArticle(args.article)
        return binding.root
    }

    private fun getRelatedNews() {
        viewModel.singleArticle.observe(viewLifecycleOwner, relatedNewsObserver)
    }

    private val relatedNewsObserver = Observer<Article> {
        if (it.events?.isNotEmpty() == true) {
            viewModel.getEvents(it.events)
        }
    }
}