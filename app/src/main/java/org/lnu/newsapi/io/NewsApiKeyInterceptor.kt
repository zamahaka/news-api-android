package org.lnu.newsapi.io

import okhttp3.Interceptor
import okhttp3.Response

class NewsApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .addHeader(AUTHORIZATION, API_KEY)
            .build()
    )


    companion object {
        private const val AUTHORIZATION = "X-Api-Key"
        private const val API_KEY = "1ef8d1f371c846d58b14190dbbf95fd7"
    }

}