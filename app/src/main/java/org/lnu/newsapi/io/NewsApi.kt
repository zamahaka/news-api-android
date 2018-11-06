package org.lnu.newsapi.io

import org.lnu.newsapi.model.Headlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun topHeadlines(
        @Query("country") countryCode: String = "us"
    ) : Call<Headlines>

}