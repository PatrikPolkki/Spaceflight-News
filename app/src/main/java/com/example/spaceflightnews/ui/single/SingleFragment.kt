package com.example.spaceflightnews.ui.single

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
import com.example.spaceflightnews.databinding.FragmentSingleBinding
import com.example.spaceflightnews.ui.ArticleListState
import com.example.spaceflightnews.ui.main.CellClickListener

class SingleFragment : Fragment(), CellClickListener {

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
        binding.relatedLaunchesRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = SingleAdapter(this@SingleFragment)
        }
        binding.relatedEventsRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = SingleAdapter(this@SingleFragment)
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
        val adapter = binding.relatedLaunchesRv.adapter as SingleAdapter
        val relatedNewsList = it.articles.filter { relatedArticle ->
            relatedArticle.id != viewModel.singleArticle.value?.id ?: return@Observer
        }
        adapter.submitList(relatedNewsList)
    }
    private val eventsListObserver = Observer<ArticleListState> {
        val adapter = binding.relatedEventsRv.adapter as SingleAdapter
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

    override fun onCellClickListener(article: Article) {
        val action = SingleFragmentDirections.actionSingleFragmentSelf(article)
        this.findNavController().navigate(action)
    }
}