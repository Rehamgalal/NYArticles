package com.nytimes.mostpopular.api

import com.nytimes.mostpopular.model.MostPopularArticles
import com.nytimes.mostpopular.other.Constants.API_KEY
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface NYApi {

    @GET("1.json")
     fun getArticles(@Query("api-key") apiKey: String = API_KEY): Single<MostPopularArticles>
}