package org.lnu.newsapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_headline.*
import org.lnu.newsapi.model.Headline

class HeadlineViewHolder private constructor(
    override val containerView: View,
    val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener {
            adapterPosition.takeUnless { position -> position == -1 }?.let(onClick)
        }
    }


    fun bind(headline: Headline) {
        txtTitle.text = headline.title ?: ""
        txtDescription.text = headline.description ?: headline.content ?: ""

        txtDescription.visibility = when {
            txtDescription.text.isBlank() -> View.GONE

            else -> View.VISIBLE
        }

        headline.urlToImage?.takeIf(String::isNotEmpty)?.let { url ->
            Picasso.with(itemView.context)
                .load(url)
                .into(ivImage)
        } ?: ivImage.setImageResource(R.drawable.news)
    }


    companion object {
        operator fun invoke(parent: ViewGroup, onClick: (Int) -> Unit) =
            HeadlineViewHolder(
                containerView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_headline, parent, false),
                onClick = onClick
            )
    }

}
