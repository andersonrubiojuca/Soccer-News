package com.example.soccernews.ui.adapter

import android.content.Intent
import android.graphics.Color.blue
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.soccernews.domain.News
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.soccernews.R
import com.example.soccernews.databinding.NewsItemBinding
import com.squareup.picasso.Picasso

class NewsAdapter(private val news: List<News>, private val listener: FavoriteListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context

        val new = news[position]
        holder.binding.tvTitle.text = new.title
        holder.binding.tvDescription.text = new.description
        Picasso.get().load(new.image)
            .fit()
            .into(holder.binding.tvThumbnail)
        //abrir link
        holder.binding.tvOpenlink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(new.link)
            context.startActivity(i)
        }
        //compartilhar
        holder.binding.tvShare.setOnClickListener {
            val i = Intent()
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, new.title)
            i.putExtra(Intent.EXTRA_TEXT, new.link)
            context.startActivity(Intent.createChooser(i, "Share via"))
        }
        //favoritos
        holder.binding.tvFavorite.setOnClickListener {
            new.favorite = !new.favorite
            this.listener.onClick(new)
            notifyItemChanged(position)
        }
        val favColor = if (new.favorite) R.color.favorite_active else R.color.favorite_default
        holder.binding.tvFavorite.setColorFilter(context.getColor(favColor))
    }

    override fun getItemCount(): Int {
        return news.size
    }

    class ViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

}
    interface FavoriteListener {
        fun onClick(news: News)
    }
