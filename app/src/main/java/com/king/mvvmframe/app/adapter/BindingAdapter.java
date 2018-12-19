package com.king.mvvmframe.app.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.king.base.adapter.HolderRecyclerAdapter;
import com.king.mvvmframe.BR;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BindingAdapter<T,VDB extends ViewDataBinding> extends HolderRecyclerAdapter<T,BindingHolder<VDB>> {

    private @LayoutRes int mLayoutId;

    public BindingAdapter(Context context, List<T> listData,@LayoutRes int layoutId) {
        super(context, listData);
        this.mLayoutId = layoutId;
    }

    @Override
    public View buildConvertView(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
        return inflate(mLayoutId,parent,false);
    }

    @Override
    public BindingHolder<VDB> buildHolder(View convertView, int viewType) {
        return new BindingHolder<>(convertView);
    }

    @Override
    public void bindViewDatas(BindingHolder<VDB> holder, T t, int position) {
            holder.mBinding.setVariable(BR.data,t);
            holder.mBinding.executePendingBindings();
    }

    public T getItem(int position) {
        if(position<getItemCount()){
            return getListData().get(position);
        }

        return null;
    }

    public void refreshData(List<T> list){
        if(list!=null){
            setListData(list);
        }else{
            getListData().clear();
        }
        notifyDataSetChanged();

    }
}
