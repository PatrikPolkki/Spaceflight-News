package com.example.spaceflightnews.ui.single

import android.os.Bundle
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
        viewModel.addArticle(args.article)
        getRelatedNews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.eventArticles.observe(viewLifecycleOwner, eventsListObserver)
        viewModel.launchArticles.observe(viewLifecycleOwner, launchesListObserver)
    }

    private fun getRelatedNews() {
        viewModel.singleArticle.observe(viewLifecycleOwner, relatedNewsObserver)
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

    private val relatedNewsObserver = Observer<Article> {
        if (it.events.isNotEmpty()) {
            viewModel.getEvents(it.events)
        }
        if (it.launches.isNotEmpty()) {
            viewModel.getLaunches(it.launches)
        } else {
            return@Observer
        }
    }

    override fun onCellClickListener(article: Article) {
        val action = SingleFragmentDirections.actionSingleFragmentSelf(article)
        this.findNavController().navigate(action)
    }
}