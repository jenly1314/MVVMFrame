package com.king.mvvmframe.app.oil.lite;

import android.os.Bundle;

import com.king.base.adapter.divider.DividerItemDecoration;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.livedata.MessageEvent;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.mvvmframe.R;
import com.king.mvvmframe.app.adapter.BindingAdapter;
import com.king.mvvmframe.bean.OilPrice;
import com.king.mvvmframe.databinding.OilPriceLiteActivityBinding;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * 精简版与标准版Activity代码基本一致，只是ViewModel不同
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
public class OilPriceLiteActivity extends BaseActivity<OilPriceLiteViewModel, OilPriceLiteActivityBinding> {

    private BindingAdapter<OilPrice> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.oil_price_lite_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getViewDataBinding().recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL,R.drawable.list_divider_8));

        mAdapter = new BindingAdapter<>(getContext(),R.layout.rv_oil_price_item);

        getViewDataBinding().recyclerView.setAdapter(mAdapter);

        getViewModel().getOilLiveData().observe(this, list -> mAdapter.refreshData(list));
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
        getViewDataBinding().srl.setOnRefreshListener(()-> getViewModel().getOilPriceInfo());
    }
}
