package com.king.mvvmframe.app.poetrylite;

import android.os.Bundle;

import com.king.base.adapter.divider.DividerItemDecoration;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.livedata.MessageEvent;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.mvvmframe.R;
import com.king.mvvmframe.app.adapter.BindingAdapter;
import com.king.mvvmframe.app.likepoetry.PoetryInfoDialogFragment;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.databinding.PoetryLiteActivityBinding;
import com.king.mvvmframe.databinding.RvPoetryItemBinding;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import timber.log.Timber;

/**
 * 精简版与标准版Activity代码基本一致，只是ViewModel不同
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryLiteActivity extends BaseActivity<PoetryLiteViewModel,PoetryLiteActivityBinding> {

    private BindingAdapter<PoetryInfo, RvPoetryItemBinding> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.poetry_lite_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getViewDataBinding().setViewModel(getViewModel());

        getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getViewDataBinding().recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL,R.drawable.list_divider_8));

        mAdapter = new BindingAdapter<>(getContext(),R.layout.rv_poetry_item);

        getViewDataBinding().recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position) -> {
            PoetryInfo data = mAdapter.getItem(position);
            showDialogFragment(PoetryInfoDialogFragment.newInstance(data));
        });

        getViewModel().getPoetryLiveData().observe(this, list -> mAdapter.refreshData(list));
        //原始使用方式
        getViewModel().getStatusEvent().observe(this, (StatusEvent.StatusObserver) status -> {
            switch (status){
                case StatusEvent.Status.LOADING:
                    if(!getViewDataBinding().srl.isRefreshing()){
                        showLoading();
                    }
                    break;
                case StatusEvent.Status.SUCCESS:
                case StatusEvent.Status.FAILURE:
                case StatusEvent.Status.ERROR:
                    hideLoading();
                    getViewDataBinding().srl.setRefreshing(false);
                    break;
            }
        });
        //原始使用方式
        getViewModel().getMessageEvent().observe(this, (MessageEvent.MessageObserver) message -> {
            Timber.d("message:%s" , message);
            ToastUtils.showToast(getContext(), message);

        });
        getViewDataBinding().srl.setOnRefreshListener(()-> getViewModel().getPoetryInfo());
    }
}
