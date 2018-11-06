package org.lnu.newsapi

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) = with(outRect) {
        left = spacing
        right = spacing
        top = spacing

        bottom = when (parent.getChildAdapterPosition(view)) {
            parent.adapter?.itemCount?.minus(1) ?: -1 -> spacing

            else -> 0
        }
    }
}