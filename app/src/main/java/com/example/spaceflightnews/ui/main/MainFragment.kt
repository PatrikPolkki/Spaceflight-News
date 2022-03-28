package com.example.spaceflightnews.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceflightnews.data.model.Articles
import com.example.spaceflightnews.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        viewModel.articleResults.observe(viewLifecycleOwner) {
            Log.d("RETROFIT", it.toString())
        }

        binding.articleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MainAdapter()

            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
        setArticleListToRv()
        
        return binding.root
    }

    private fun setArticleListToRv() {
        viewModel.articleResults.observe(viewLifecycleOwner, articleListObserver)
    }

    private val articleListObserver = Observer<List<Articles>> {
        val adapter = binding.articleRv.adapter as MainAdapter
        adapter.submitList(it)
    }
}