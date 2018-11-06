package org.lnu.newsapi

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_headline.*
import kotlinx.android.synthetic.main.content_headline.*
import org.lnu.newsapi.model.Headline
import android.content.Intent as AndroidIntent

class HeadlineActivity : AppCompatActivity() {

    private val headline by lazy { intent.getParcelableExtra<Headline>(HEADLINE) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_headline)
        setSupportActionBar(toolbar)

        txtTitle.text = headline.title ?: ""
        txtDescription.text = headline.description ?: ""
        txtContent.text = headline.content ?: ""

        headline.urlToImage?.let { url ->
            Picasso.with(this)
                .load(url)
                .into(ivImage)
        }

        if (headline.url == null) (fab as View).visibility = View.GONE

        fab.setOnClickListener { share() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_headline, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean = !headline.url.isNullOrBlank()

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_open_in_browser -> {
            openInBrowser()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }


    private fun openInBrowser() {
        val intent = AndroidIntent(
            AndroidIntent.ACTION_VIEW, Uri.parse(headline.url!!)
        )

        if (intent.resolveActivity(packageManager) != null) startActivity(intent)
    }

    private fun share() {
        val intent = AndroidIntent(AndroidIntent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(AndroidIntent.EXTRA_SUBJECT, "News Url")
            putExtra(AndroidIntent.EXTRA_TEXT, headline.url!!)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(android.content.Intent.createChooser(
                intent, getString(R.string.share_with)
            ))
        }
    }


    companion object {
        private const val HEADLINE = "headline"

        @Suppress("FunctionName")
        fun Intent(context: Context, headline: Headline): AndroidIntent =
            AndroidIntent(context, HeadlineActivity::class.java)
                .putExtra(HEADLINE, headline)
    }

}
