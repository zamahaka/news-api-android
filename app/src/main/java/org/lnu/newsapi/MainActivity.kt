package org.lnu.newsapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.lnu.newsapi.model.Headlines
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val newsApi get() = (application as NewsApp).newsApi


    private val headlinesAdapter = HeadlinesAdapter {
        startActivity(HeadlineActivity.Intent(context = this, headline = it))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHeadlines.apply {
            adapter = headlinesAdapter

            addItemDecoration(
                SpaceItemDecoration(
                    spacing = resources.getDimensionPixelOffset(R.dimen.headline_spacing)
                )
            )
        }

        refreshLayout.setOnRefreshListener(this::fetch)

        fetch()
    }


    private fun fetch() {
        refreshLayout.isRefreshing = true

        newsApi.topHeadlines().enqueue(object : Callback<Headlines> {
            override fun onFailure(call: Call<Headlines>, t: Throwable?) {
                refreshLayout.isRefreshing = false

                Toast.makeText(
                    this@MainActivity,
                    t?.message?.takeIf(String::isNotBlank) ?: "Blank failure exception message",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                refreshLayout.isRefreshing = false

                response.body()?.headlines?.let(headlinesAdapter::submitList)
                    ?: run {
                        Toast.makeText(
                            this@MainActivity,
                            response.errorBody()?.string() ?: "no response body",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        })
    }

}
