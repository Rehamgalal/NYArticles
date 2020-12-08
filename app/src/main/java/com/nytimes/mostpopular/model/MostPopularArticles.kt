package com.nytimes.mostpopular.model

data class MostPopularArticles(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)