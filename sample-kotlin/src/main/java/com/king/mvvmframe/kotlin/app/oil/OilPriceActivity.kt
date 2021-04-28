package com.king.mvvmframe.kotlin.app.oil

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.base.util.ToastUtils
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.mvvmframe.kotlin.R
import com.king.mvvmframe.kotlin.app.adapter.BindingAdapter
import com.king.mvvmframe.kotlin.bean.OilPrice
import com.king.mvvmframe.kotlin.databinding.OilPriceActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class OilPriceActivity : BaseActivity<OilPriceViewModel,OilPriceActivityBinding>() {

    private val mAdapter by lazy { BindingAdapter<OilPrice>(context,R.layout.rv_oil_price_item) }

    override fun getLayoutId(): Int {
        return R.layout.oil_price_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        registerMessageEvent {
            ToastUtils.showToast(this,it)
        }

        with(viewDataBinding.recyclerView){
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL,R.drawable.list_divider_8))
            adapter = mAdapter
        }

        viewModel.oilLiveData.observe(this, Observer {
            mAdapter.refreshData(it)
        })

        viewModel.getOilPriceInfo()
    }


    override fun showLoading() {
        if(!viewDataBinding.srl.isRefreshing){
            viewDataBinding.srl.isRefreshing = true
        }
    }

    override fun hideLoading() {
        viewDataBinding.srl.isRefreshing = false
    }
}