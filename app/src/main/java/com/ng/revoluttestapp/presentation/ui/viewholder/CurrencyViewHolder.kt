package com.ng.revoluttestapp.presentation.ui.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ng.revoluttestapp.R
import com.ng.revoluttestapp.domain.entity.CurrencyEntity

class CurrencyViewHolder(
    context: Context,
    parent: ViewGroup,
    private val onClick: (CurrencyEntity) -> Unit
) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_currency, parent, false)) {

    private val photo = find<TextView>(R.id.currency_photo)
    private val title = find<TextView>(R.id.currency_title)
    private val subtitle = find<TextView>(R.id.currency_subtitle)
    private val rate = find<TextView>(R.id.currency_rate_and_count)

    private var data: CurrencyEntity? = null

    init {
        itemView.setOnClickListener {
            data?.let { item ->
                if (!item.isSelect)
                    onClick.invoke(item)
            }
        }
    }

    fun bind(data: CurrencyEntity) {
        this.data = data

        setPhoto()
        setTitle()
        setSubtitle()
        setRate()
    }

    private fun setPhoto() {
        photo.setText(R.string.common_currency_photo_place)
    }

    private fun setTitle() {
        title.text = data?.currencyName
    }

    private fun setSubtitle() {
        subtitle.setText(R.string.common_currency_subtitle)
    }

    private fun setRate() {
        data?.let { item ->
            rate.apply {
                isFocusable = item.isSelect
                isClickable = item.isSelect
                isFocusableInTouchMode = item.isSelect
                isLongClickable = item.isSelect
            }
            if (!item.isSelect) {
                rate.text = item.rate.toString()
            } else if (rate.text.toString().isEmpty()) {
                rate.text = "1"
            }
        }
    }

    private fun <T : View> find(resId: Int) = itemView.findViewById<T>(resId)
}