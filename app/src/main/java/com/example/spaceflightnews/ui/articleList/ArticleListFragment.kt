package com.example.spaceflightnews.ui.articleList

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
import com.example.spaceflightnews.databinding.FragmentArticleListBinding
import com.example.spaceflightnews.ui.ArticleClickListener
import com.example.spaceflightnews.ui.ArticleListState


class ArticleListFragment : Fragment(), ArticleClickListener {
    private var _binding: FragmentArticleListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticleListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleListBinding.inflate(layoutInflater)
        binding.apply {
            articleListViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.articleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ArticleListAdapter(this@ArticleListFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setArticleListToRv() {
        viewModel.articleResults.observe(viewLifecycleOwner, articleListObserver)
    }

    private val articleListObserver = Observer<ArticleListState> {
        val adapter = binding.articleRv.adapter as ArticleListAdapter
        adapter.submitList(it.articles)
    }

    override fun onArticleClickListener(article: Article) {
        val action =
            ArticleListFragmentDirections.actionArticleListFragmentToArticleFragment(article)
        this.findNavController().navigate(action)
    }
}