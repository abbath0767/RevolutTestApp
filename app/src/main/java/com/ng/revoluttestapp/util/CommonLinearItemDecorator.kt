package com.ng.revoluttestapp.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ng.revoluttestapp.R

class CommonLinearItemDecorator(private val context: Context) : RecyclerView.ItemDecoration() {

    private val marginHorizontal = context.resources.getDimension(R.dimen.common_16).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(marginHorizontal, 0, marginHorizontal, 0)
    }
}