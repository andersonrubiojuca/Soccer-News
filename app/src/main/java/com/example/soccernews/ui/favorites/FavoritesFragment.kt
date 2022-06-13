package com.example.soccernews.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soccernews.databinding.FragmentNewsBinding
import com.example.soccernews.domain.News
import com.example.soccernews.ui.adapter.FavoriteListener
import com.example.soccernews.ui.adapter.NewsAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var news: List<News>
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[FavoritesViewModel::class.java]

        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        setObservers()

        return binding.root
    }

    private fun setObservers() {
        binding.rvNews.layoutManager = LinearLayoutManager(context)

        loadFavoriteNews()

    }

    private fun loadFavoriteNews(){
        viewModel.news.observe(viewLifecycleOwner) { action ->
            viewModel.news.value?.let {
                news = it
            }

            binding.rvNews.adapter = NewsAdapter(action, object : FavoriteListener {
                override fun onClick(news: News) {
                    viewModel.saveNews(news)
                    loadFavoriteNews()
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}