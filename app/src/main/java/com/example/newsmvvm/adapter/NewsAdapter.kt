package com.example.newsmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsmvvm.R
import com.example.newsmvvm.api.ArticlesItem

class NewsAdapter(var newsList: List<ArticlesItem?>?): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_news,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsList = newsList?.get(position)
        holder.title.text = newsList?.title
        holder.date.text = newsList?.publishedAt
        holder.desc.text = newsList?.description
        Glide.with(holder.itemView).load(newsList?.urlToImage)
            .into(holder.image)
    }

    fun changeData(newsList: List<ArticlesItem?>?){
        this .newsList = newsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsList?.size?:0
    }
}