package com.king.mvvmframe.app.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
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
        if (list != null) {
            setListData(list)
        } else {
            listData.clear()
        }
        notifyDataSetChanged()
    }
}