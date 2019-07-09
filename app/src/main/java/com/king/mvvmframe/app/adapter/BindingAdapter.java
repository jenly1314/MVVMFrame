package com.king.mvvmframe.app.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

import com.king.base.adapter.BaseRecyclerAdapter;
import com.king.mvvmframe.BR;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BindingAdapter<T,VDB extends ViewDataBinding> extends BaseRecyclerAdapter<T,BindingHolder<VDB>> {

    public BindingAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public BindingAdapter(Context context, List<T> listData, @LayoutRes int layoutId) {
        super(context, listData,layoutId);
    }

    @Override
    public void bindViewDatas(BindingHolder<VDB> holder, T item, int position) {
        holder.mBinding.setVariable(BR.data,item);
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
