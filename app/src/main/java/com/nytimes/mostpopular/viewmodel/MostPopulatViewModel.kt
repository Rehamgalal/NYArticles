package com.nytimes.mostpopular.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nytimes.mostpopular.api.NYApi
import com.nytimes.mostpopular.db.ArticleEntity
import com.nytimes.mostpopular.db.ArticlesDatabase
import com.nytimes.mostpopular.model.datasource.ArticleDataSource
import com.nytimes.mostpopular.other.Constants.ARTICLES_PER_PAGE
import com.nytimes.mostpopular.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MostPopulatViewModel @ViewModelInject constructor(
    private val apiService: NYApi,
    private val articlesDb: ArticlesDatabase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var articlesPagedList: LiveData<PagedList<ArticleEntity>>

    private val boundaryCallback: ArticleDataSource =
        ArticleDataSource(
            apiService,
            articlesDb,
            compositeDisposable
        )

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(ARTICLES_PER_PAGE)
            .setEnablePlaceholders(false)
            .build()

        articlesPagedList =
            LivePagedListBuilder(articlesDb.articlesDao().allArticles(), config)
                .setBoundaryCallback(boundaryCallback)
                .build()
    }

    fun listIsEmpty(): Boolean {
        return articlesPagedList.value?.isEmpty() ?: true
    }

    fun retry() {
        boundaryCallback.retry()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return boundaryCallback.networkState
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}