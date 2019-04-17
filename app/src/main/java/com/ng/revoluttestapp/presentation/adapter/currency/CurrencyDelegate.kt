package com.ng.revoluttestapp.presentation.adapter.currency

import android.content.Context
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.presentation.adapter.BaseItem
import com.ng.revoluttestapp.presentation.ui.viewholder.CurrencyViewHolder
import com.ng.revoluttestapp.presentation.ui.viewholder.RateTextWatcher

class CurrencyDelegate(
    private val context: Context,
    private val onClick: (CurrencyEntity) -> Unit,
    onRateChange: (Double) -> Unit
) : AbsListItemAdapterDelegate<CurrencyEntity, BaseItem, CurrencyViewHolder>() {

    private val rateTextWatcher = RateTextWatcher(onRateChange)

    override fun isForViewType(item: BaseItem, items: MutableList<BaseItem>, position: Int) =
        item is CurrencyEntity

    override fun onCreateViewHolder(parent: ViewGroup) = CurrencyViewHolder(context, parent, onClick, rateTextWatcher)

    override fun onBindViewHolder(item: CurrencyEntity, holder: CurrencyViewHolder, payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            holder.bind(item)
        else
            holder.changeView(item)
    }
}