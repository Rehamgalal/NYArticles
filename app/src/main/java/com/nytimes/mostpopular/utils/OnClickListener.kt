package com.nytimes.mostpopular.utils

import com.nytimes.mostpopular.db.ArticleEntity


interface OnClickListener {
    fun onArticleCLicked(article: ArticleEntity)
}