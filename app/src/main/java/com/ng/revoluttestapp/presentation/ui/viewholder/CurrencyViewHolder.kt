package com.ng.revoluttestapp.presentation.ui.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ng.revoluttestapp.R
import com.ng.revoluttestapp.domain.entity.CurrencyEntity

class CurrencyViewHolder(
    context: Context,
    parent: ViewGroup,
    private val onClick: (CurrencyEntity) -> Unit,
    private val rateTextWatcher: RateTextWatcher
) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_currency, parent, false)) {

    private val photo = find<TextView>(R.id.currency_photo)
    private val title = find<TextView>(R.id.currency_title)
    private val subtitle = find<TextView>(R.id.currency_subtitle)
    private val rate = find<EditText>(R.id.currency_rate_and_count)

    private var data: CurrencyEntity? = null

    init {
        itemView.setOnClickListener {
            data?.let { item ->
                if (!item.isSelect) {
                    onClick.invoke(item)
                    title.clearFocus()
                }
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

    fun changeView(data: CurrencyEntity) {
        this.data = data
        setRate(true)
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

    private fun setRate(afterUpdate: Boolean = false) {
        data?.let { item ->
            enableTextWatcher(item.isSelect)
            if (rate.isEnabled != item.isSelect) {
                rate.apply {
                    isEnabled = item.isSelect
                    isFocusable = item.isSelect
                    isClickable = item.isSelect
                    isFocusableInTouchMode = item.isSelect
                    isLongClickable = item.isSelect
                }
            }
//            rate.apply {
//                isEnabled = item.isSelect
//                isFocusable = item.isSelect
//                isClickable = item.isSelect
//                isFocusableInTouchMode = item.isSelect
//                isLongClickable = item.isSelect
//            }

            if (item.isSelect) {
                if (afterUpdate)
                    return
                rateTextWatcher.selfChange {
                    rate.setText(item.rate.toString())
                }
            } else {
                rate.setText(item.rate.toString())
            }
        }
    }

    private fun enableTextWatcher(isEnable: Boolean) {
        rate.removeTextChangedListener(rateTextWatcher)
        if (isEnable) {
            rate.addTextChangedListener(rateTextWatcher)
        }
    }

    private fun <T : View> find(resId: Int) = itemView.findViewById<T>(resId)
}