package com.king.mvvmframe.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.king.base.adapter.BaseRecyclerAdapter
import com.king.mvvmframe.BR

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class BindingAdapter<T> : BaseRecyclerAdapter<T, BindingHolder<ViewDataBinding>> {
    constructor(context: Context, layoutId: Int) : super(context, layoutId)
    constructor(context: Context, listData: MutableList<T>?, layoutId: Int) : super(
        context,
        listData,
        layoutId
    )

    override fun bindViewDatas(holder: BindingHolder<ViewDataBinding>, item: T, position: Int) {
        holder.binding?.also {
            it.setVariable(BR.data, item)
            it.executePendingBindings()
        }
    }

    fun getItem(position: Int): T? {
        return listData.getOrNull(position)
    }

    fun refreshData(list: List<T>?) {
        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(listData, list.orEmpty()))
        if (list != null) {
            setListData(list)
        } else {
            listData.clear()
        }
        diffResult.dispatchUpdatesTo(this)
    }

    class ItemDiffCallback<T>(
        private val oldList: List<T>,
        private val newList: List<T>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }
    }
}
