package org.lnu.newsapi

import android.app.Application
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.lnu.newsapi.io.NewsApi
import org.lnu.newsapi.io.NewsApiKeyInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApp : Application() {

    val newsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(NewsApiKeyInterceptor())
                    .addInterceptor(HttpLoggingInterceptor {
                        Log.d("myLog-network", it)
                    }.apply { level = HttpLoggingInterceptor.Level.BASIC })
                    .build()
            )
            .build()
            .create(NewsApi::class.java)
    }

}