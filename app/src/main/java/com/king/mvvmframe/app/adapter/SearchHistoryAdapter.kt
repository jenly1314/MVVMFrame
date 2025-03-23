package com.king.mvvmframe.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.king.mvvmframe.R
import com.king.mvvmframe.constant.Constants
import com.king.mvvmframe.data.model.SearchHistory
import com.king.mvvmframe.util.RandomUtil.randomColor
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class SearchHistoryAdapter(list: MutableList<SearchHistory>) : TagAdapter<SearchHistory>(list) {

    override fun getView(parent: FlowLayout, position: Int, data: SearchHistory): View {
        val tv = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_history_item, parent, false) as TextView
        tv.text = data.word
        tv.setTextColor(randomColor(Constants.COLOR_RGB_MIN, Constants.COLOR_RGB_MAX))
        return tv
    }
}
