package com.king.mvvmframe.app.oil;

import android.os.Bundle;

import com.king.base.adapter.divider.DividerItemDecoration;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.mvvmframe.R;
import com.king.mvvmframe.app.adapter.BindingAdapter;
import com.king.mvvmframe.bean.OilPrice;
import com.king.mvvmframe.databinding.OilPriceActivityBinding;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
public class OilPriceActivity extends BaseActivity<OilPriceViewModel, OilPriceActivityBinding> {

    private BindingAdapter<OilPrice> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.oil_price_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getViewDataBinding().recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL,R.drawable.list_divider_8));

        mAdapter = new BindingAdapter<>(getContext(),R.layout.rv_oil_price_item);

        getViewDataBinding().recyclerView.setAdapter(mAdapter);

        getViewModel().getOilLiveData().observe(this, list -> mAdapter.refreshData(list));
        //添加registerStatusEvent后使用方式，推荐
        registerStatusEvent(status -> {
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
        //添加registerMessageEvent后使用方式,推荐
        registerMessageEvent(message -> {
            Timber.d("message:%s" , message);
            ToastUtils.showToast(getContext(), message);
        });

        getViewDataBinding().srl.setOnRefreshListener(()-> getViewModel().getOilPriceInfo());
    }
}
