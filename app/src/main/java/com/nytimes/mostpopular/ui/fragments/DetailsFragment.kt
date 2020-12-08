package com.nytimes.mostpopular.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nytimes.mostpopular.R
import com.nytimes.mostpopular.db.ArticleEntity
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article : ArticleEntity = arguments?.getParcelable<ArticleEntity>("article") !!
        Glide.with(this)
            .load(article.media)
            .placeholder(R.drawable.placeholder)
            .into(image)
        title.text = article.title
        abstract_field.text = article.abstractField
        date_published.text = "Date Published: " +article.published_date
    }
}