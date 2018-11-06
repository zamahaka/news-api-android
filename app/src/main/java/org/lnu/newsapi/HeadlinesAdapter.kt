package org.lnu.newsapi

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.lnu.newsapi.model.Headline

class HeadlinesAdapter(
    private val onHeadlineClicked: (Headline) -> Unit
) : ListAdapter<Headline, HeadlineViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeadlineViewHolder(parent = parent) { position ->
            onHeadlineClicked(getItem(position))
        }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) =
        holder.bind(getItem(position))


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Headline>() {
            override fun areItemsTheSame(oldItem: Headline, newItem: Headline) =
                oldItem.source == newItem.source

            override fun areContentsTheSame(oldItem: Headline, newItem: Headline) =
                oldItem == newItem
        }
    }

}
