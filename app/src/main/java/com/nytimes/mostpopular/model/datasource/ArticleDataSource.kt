package com.nytimes.mostpopular.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nytimes.mostpopular.api.NYApi
import com.nytimes.mostpopular.db.ArticleEntity
import com.nytimes.mostpopular.db.ArticlesDatabase
import com.nytimes.mostpopular.model.toArticleEntity
import com.nytimes.mostpopular.utils.NetworkState
import com.nytimes.mostpopular.utils.PagingRequestHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class ArticleDataSource (private val apiService: NYApi,
                         private val articlesDb: ArticlesDatabase,
                         val compositeDisposable: CompositeDisposable):PagedList.BoundaryCallback<ArticleEntity>(){
    val networkState = MutableLiveData<NetworkState>()

    private val executor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(executor)
    private lateinit var rType: PagingRequestHelper.RequestType

    fun retry() {
        fetchAndStoreMovies(rType)
    }

    override fun onZeroItemsLoaded() {
        fetchAndStoreMovies(PagingRequestHelper.RequestType.INITIAL)
    }


    private fun fetchAndStoreMovies(rType: PagingRequestHelper.RequestType) {
        this.rType = rType

        helper.runIfNotRunning(rType) { callback ->
            networkState.postValue(NetworkState.LOADING)

            compositeDisposable.add(apiService.getArticles()
                .map { response -> response.results.map { it.toArticleEntity() } }
                .doOnSuccess {
                    articlesDb.articlesDao().insert(it)
                }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        networkState.postValue(NetworkState.LOADED)
                        callback.recordSuccess()
                    },
                    {
                        networkState.postValue(NetworkState.error(it.message))
                        callback.recordFailure(it)
                    }
                ))
        }
    }
}


