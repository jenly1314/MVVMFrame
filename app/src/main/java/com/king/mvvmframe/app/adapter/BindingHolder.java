package com.king.mvvmframe.app.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.king.base.adapter.holder.ViewHolder;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BindingHolder<VDB extends ViewDataBinding> extends ViewHolder {

    VDB mBinding;

    public BindingHolder(View convertView) {
        super(convertView);
        mBinding = DataBindingUtil.bind(convertView);

    }

    public VDB getBinding(){
        return mBinding;
    }
}
