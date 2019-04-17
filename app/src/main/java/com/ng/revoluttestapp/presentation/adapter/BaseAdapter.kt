package com.ng.revoluttestapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

open class BaseAdapter<T : BaseItem>(delegateManager: AdapterDelegatesManager<List<T>>) :
    ListDelegationAdapter<List<T>>(delegateManager) {

    init {
        super.setItems(emptyList())
    }

    override fun setItems(newList: List<T>) {
        val oldList = getItems().toList()

        val diff = DiffUtil.calculateDiff(getDiffUtil(newList, oldList))
        super.setItems(newList)
        diff.dispatchUpdatesTo(this)
    }

    open fun getDiffUtil(newList: List<T>, oldList: List<T>): DiffUtilBase<out BaseItem> {
        return DiffUtilBase(newList, oldList)
    }

    open class DiffUtilBase<T : BaseItem>(
        protected val newList: List<T>,
        protected val oldList: List<T>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}