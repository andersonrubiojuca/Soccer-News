package com.example.soccernews.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soccernews.R
import com.example.soccernews.databinding.FragmentNewsBinding
import com.example.soccernews.domain.News
import com.example.soccernews.ui.adapter.FavoriteListener
import com.example.soccernews.ui.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        newsViewModel =
            ViewModelProvider(this)[NewsViewModel::class.java]

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setObservers()
        setListeners()

        return root
    }


    private fun setObservers() {

        observeNews()

        newsViewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                NewsViewModel.State.DOING -> binding.srlNews.isRefreshing = true
                NewsViewModel.State.DONE -> binding.srlNews.isRefreshing = false
                else -> {
                    Snackbar.make(binding.srlNews, getString(R.string.network_error), Snackbar.LENGTH_SHORT).show()
                    binding.srlNews.isRefreshing = false
                }
            }
        }
    }

    private fun observeNews() {
        newsViewModel.news.observe(viewLifecycleOwner) { news ->
            binding.rvNews.adapter = NewsAdapter(news, object : FavoriteListener {
                override fun onClick(news: News) {
                    newsViewModel.saveNews(news)
                }
            })
        }
    }

    private fun setListeners() {
        binding.rvNews.layoutManager = LinearLayoutManager(context)

        binding.srlNews.setOnRefreshListener {
            newsViewModel.findNews()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}