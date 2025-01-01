package com.dicoding.picodiploma.loginwithanimation.view.main.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R

class NewsPagerAdapter(private val newsList: List<NewsItem>) : RecyclerView.Adapter<NewsPagerAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageNews: ImageView = itemView.findViewById(R.id.imageVaccine)
        val textNewsTitle: TextView = itemView.findViewById(R.id.textNewsTitle)
        val textNewsDescription: TextView = itemView.findViewById(R.id.textNewsDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.imageNews.setImageResource(newsItem.imageResId)
        holder.textNewsTitle.text = newsItem.title
        holder.textNewsDescription.text = newsItem.description
    }

    override fun getItemCount(): Int = newsList.size
}