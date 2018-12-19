package com.king.mvvmframe.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.king.mvvmframe.R;
import com.king.mvvmframe.app.Constants;
import com.king.mvvmframe.bean.SearchHistory;
import com.king.mvvmframe.util.RandomUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class SearchHistoryAdapter extends TagAdapter<SearchHistory> {
    private Context mContext;
    private LayoutInflater mInflater;

    public SearchHistoryAdapter(Context context, List<SearchHistory> datas) {
        super(datas);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, SearchHistory data) {
        TextView tv = (TextView) mInflater.inflate(R.layout.search_history_item,parent,false);
        tv.setText(data.getName());
        tv.setTextColor(RandomUtils.INSTANCE.randomColor(Constants.COLOR_RGB_MIN,Constants.COLOR_RGB_MAX));
        return tv;
    }

}