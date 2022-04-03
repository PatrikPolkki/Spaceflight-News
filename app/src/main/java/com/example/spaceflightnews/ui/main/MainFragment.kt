package com.example.spaceflightnews.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.databinding.FragmentMainBinding
import com.example.spaceflightnews.ui.ArticleListState


class MainFragment : Fragment(), CellClickListener {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.apply {
            mainViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.articleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MainAdapter(this@MainFragment)

            /*
            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
             */
        }
        setArticleListToRv()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getArticles(7)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setArticleListToRv() {
        viewModel.articleResults.observe(viewLifecycleOwner, articleListObserver)
    }

    private val articleListObserver = Observer<ArticleListState> {
        val adapter = binding.articleRv.adapter as MainAdapter
        adapter.submitList(it.articles)
    }

    override fun onCellClickListener(article: Article) {
        val action = MainFragmentDirections.actionMainFragmentToSingleFragment(article)
        this.findNavController().navigate(action)
    }
}