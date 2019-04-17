package com.ng.revoluttestapp.presentation.adapter.currency

import android.os.Bundle
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.presentation.adapter.BaseAdapter
import com.ng.revoluttestapp.presentation.adapter.BaseItem

class CurrencyAdapter(delegateManager: AdapterDelegatesManager<List<BaseItem>>) :
    BaseAdapter<BaseItem>(delegateManager) {

    override fun getDiffUtil(newList: List<BaseItem>, oldList: List<BaseItem>): DiffUtilBase<out BaseItem> {
        return DiffUtilCurrency(newList, oldList)
    }

    class DiffUtilCurrency(newList: List<BaseItem>, oldList: List<BaseItem>) :
        BaseAdapter.DiffUtilBase<BaseItem>(newList, oldList) {

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return if (oldItem is CurrencyEntity && newItem is CurrencyEntity) {
                if (oldItem.rate != newItem.rate || oldItem.isSelect != newItem.isSelect) {
                    Bundle()
                } else {
                    null
                }
            } else
                null
        }
    }
}