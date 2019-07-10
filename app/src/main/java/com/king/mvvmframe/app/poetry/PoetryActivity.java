package com.king.mvvmframe.app.poetry;

import android.os.Bundle;

import com.king.base.adapter.divider.DividerItemDecoration;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.mvvmframe.R;
import com.king.mvvmframe.app.adapter.BindingAdapter;
import com.king.mvvmframe.app.likepoetry.PoetryInfoDialogFragment;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.databinding.PoetryActivityBinding;
import com.king.mvvmframe.databinding.RvPoetryItemBinding;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import timber.log.Timber;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryActivity extends BaseActivity<PoetryViewModel,PoetryActivityBinding> {

    private BindingAdapter<PoetryInfo, RvPoetryItemBinding> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.poetry_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL,R.drawable.list_divider_8));

        mAdapter = new BindingAdapter<>(getContext(),R.layout.rv_poetry_item);

        mBinding.recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position) -> {
            PoetryInfo data = mAdapter.getItem(position);
            showDialogFragment(PoetryInfoDialogFragment.newInstance(data));
        });

        mViewModel.getPoetryLiveData().observe(this, list -> mAdapter.refreshData(list));
        //添加registerStatusEvent后使用方式，推荐
        registerStatusEvent(status -> {
            switch (status){
                case StatusEvent.Status.LOADING:
                    if(!mBinding.srl.isRefreshing()){
                        showLoading();
                    }
                    break;
                case StatusEvent.Status.SUCCESS:
                case StatusEvent.Status.FAILURE:
                case StatusEvent.Status.ERROR:
                    hideLoading();
                    mBinding.srl.setRefreshing(false);
                    break;
            }
        });
        //添加registerMessageEvent后使用方式,推荐
        registerMessageEvent(message -> {
            Timber.d("message:%s" , message);
            ToastUtils.showToast(getContext(), message);
        });

        mBinding.srl.setOnRefreshListener(()-> mViewModel.getPoetryInfo());
    }
}
