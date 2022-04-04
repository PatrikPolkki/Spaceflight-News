package com.example.spaceflightnews.ui.article

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.databinding.FragmentArticleBinding
import com.example.spaceflightnews.ui.ArticleClickListener
import com.example.spaceflightnews.ui.ArticleListState

class ArticleFragment : Fragment(), ArticleClickListener {

    private val args: ArticleFragmentArgs by navArgs()
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(layoutInflater)
        binding.apply {
            singleViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        binding.relatedLaunchesRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RelatedArticlesAdapter(this@ArticleFragment)
        }
        binding.relatedEventsRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RelatedArticlesAdapter(this@ArticleFragment)
        }
        viewModel.addArticleToViewModel(args.article)
        getRelatedArticles()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.eventArticles.observe(viewLifecycleOwner, eventsListObserver)
        viewModel.launchArticles.observe(viewLifecycleOwner, launchesListObserver)

        binding.readMoreButton.setOnClickListener {
            viewModel.singleArticle.value?.url?.let { it1 -> openWebPage(it1) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("ACTIVITY", e.toString())
        }
    }

    private fun getRelatedArticles() {
        viewModel.singleArticle.observe(viewLifecycleOwner, relatedArticlesObserver)
    }

    private val launchesListObserver = Observer<ArticleListState> {
        val adapter = binding.relatedLaunchesRv.adapter as RelatedArticlesAdapter
        val relatedNewsList = it.articles.filter { relatedArticle ->
            relatedArticle.id != viewModel.singleArticle.value?.id ?: return@Observer
        }
        adapter.submitList(relatedNewsList)
    }
    private val eventsListObserver = Observer<ArticleListState> {
        val adapter = binding.relatedEventsRv.adapter as RelatedArticlesAdapter
        val relatedNewsList = it.articles.filter { relatedArticle ->
            relatedArticle.id != viewModel.singleArticle.value?.id ?: return@Observer
        }

        adapter.submitList(relatedNewsList)
    }

    private val relatedArticlesObserver = Observer<Article> {
        if (it.events.isNotEmpty() && viewModel.launchArticles.value == null) {
            viewModel.getEvents(it.events.first().id)
        }
        if (it.launches.isNotEmpty() && viewModel.launchArticles.value == null) {
            viewModel.getLaunches(it.launches.first().id)
        } else {
            return@Observer
        }
    }

    override fun onArticleClickListener(article: Article) {
        val action = ArticleFragmentDirections.actionArticleFragmentSelf(article)
        this.findNavController().navigate(action)
    }
}