package com.king.mvvmframe.app.poetry;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.mvvmframe.R;
import com.king.mvvmframe.databinding.PoetryActivityBinding;

import timber.log.Timber;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryActivity extends BaseActivity<PoetryViewModel,PoetryActivityBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.poetry_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);

        mViewModel.getPoetryLiveData().observe(this, poetryInfo -> {
            mBinding.setData(poetryInfo);

        });

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

        registerMessageEvent(message -> {
            Timber.d("message:%s" , message);
            ToastUtils.showToast(getContext(), message);
        });

        mBinding.srl.setOnRefreshListener(()-> mViewModel.getPoetryInfo());
    }
}
