package com.nytimes.mostpopular.adapters.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nytimes.mostpopular.R
import com.nytimes.mostpopular.db.ArticleEntity
import com.nytimes.mostpopular.utils.OnClickListener
import kotlinx.android.synthetic.main.article_item.view.*

class ArticleItemViewHolder(view: View,private val listener:OnClickListener) : RecyclerView.ViewHolder(view) {

    fun bind(article: ArticleEntity?) {
        itemView.title.text = article?.title

        Glide.with(itemView.context)
            .load(article?.media)
            .placeholder(R.drawable.placeholder)
            .into(itemView.image)
        itemView.setOnClickListener {
            listener.onArticleCLicked(article!!)
        }
    }


    companion object {
        fun create(parent: ViewGroup,listener: OnClickListener): ArticleItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.article_item, parent, false)
            return ArticleItemViewHolder(view,listener)
        }
    }
}
