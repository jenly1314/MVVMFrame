package com.king.mvvmframe.app.poetrylite;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.livedata.MessageEvent;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.mvvmframe.R;
import com.king.mvvmframe.databinding.PoetryLiteActivityBinding;

import timber.log.Timber;

/**
 * 精简版与标准版Activity代码基本一致，只是ViewModel不同
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryLiteActivity extends BaseActivity<PoetryLiteViewModel,PoetryLiteActivityBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.poetry_lite_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);

        mViewModel.getPoetryLiveData().observe(this, poetryInfo -> {
            mBinding.setData(poetryInfo);

        });
        //原始使用方式
        mViewModel.getStatusEvent().observe(this, (StatusEvent.StatusObserver) status -> {
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
        //原始使用方式
        mViewModel.getMessageEvent().observe(this, (MessageEvent.MessageObserver) message -> {
            Timber.d("message:%s" , message);
            ToastUtils.showToast(getContext(), message);

        });
        mBinding.srl.setOnRefreshListener(()-> mViewModel.getPoetryInfo());
    }
}
