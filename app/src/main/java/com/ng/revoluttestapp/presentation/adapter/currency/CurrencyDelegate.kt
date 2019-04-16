package com.ng.revoluttestapp.presentation.adapter.currency

import android.content.Context
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.presentation.adapter.BaseItem
import com.ng.revoluttestapp.presentation.ui.viewholder.CurrencyViewHolder

class CurrencyDelegate(
    private val context: Context,
    private val onClick: (CurrencyEntity) -> Unit
) : AbsListItemAdapterDelegate<CurrencyEntity, BaseItem, CurrencyViewHolder>() {

    override fun isForViewType(item: BaseItem, items: MutableList<BaseItem>, position: Int) =
        item is CurrencyEntity

    override fun onCreateViewHolder(parent: ViewGroup) = CurrencyViewHolder(context, parent, onClick)

    override fun onBindViewHolder(item: CurrencyEntity, holder: CurrencyViewHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}