package com.king.mvvmframe.app.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.king.base.adapter.holder.ViewHolder

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class BindingHolder<VDB : ViewDataBinding>(convertView: View) : ViewHolder(convertView) {

    val binding by lazy {
        DataBindingUtil.bind<VDB>(convertView)
    }
}