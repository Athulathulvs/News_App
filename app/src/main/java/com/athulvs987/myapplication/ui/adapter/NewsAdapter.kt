package com.athulvs987.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.athulvs987.myapplication.databinding.RecyclerNewsItemBinding
import com.athulvs987.myapplication.model.newsModel.NewsResponce
import com.athulvs987.myapplication.ui.loadImage
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter(val onItemClicke:(NewsResponce.Article) -> Unit):RecyclerView.Adapter<NewsAdapter.NewsVH>() {
    var articles = ArrayList<NewsResponce.Article>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val binding =RecyclerNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsVH(binding)
    }

    override fun getItemCount(): Int {
       return articles.size
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        holder.bind(articles[position])
        holder.itemView.setOnClickListener {
            onItemClicke(articles[position])
        }
    }

    inner class NewsVH( private val bindingView: RecyclerNewsItemBinding) :RecyclerView.ViewHolder(bindingView.root){
        fun bind(article: NewsResponce.Article) {
               bindingView.imgImage.loadImage(article.urlToImage ?:"")
               bindingView.txtTitle.text =article.title
               bindingView.txtContent.text = article.content
               bindingView.txtSource.text = article.author

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat =SimpleDateFormat("yyyy MMM dd HH:mm", Locale.getDefault())
            val date =inputFormat.parse(article.publishedAt)
            val formattedDate =outputFormat.format(date)
            bindingView.txtTime.text = formattedDate
        }

    }
}