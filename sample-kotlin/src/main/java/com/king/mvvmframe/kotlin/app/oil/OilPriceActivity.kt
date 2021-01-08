package com.king.mvvmframe.kotlin.app.oil

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.base.util.ToastUtils
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.mvvmframe.kotlin.R
import com.king.mvvmframe.kotlin.app.adapter.BindingAdapter
import com.king.mvvmframe.kotlin.bean.OilPrice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.oil_price_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class OilPriceActivity : BaseActivity<OilPriceViewModel,ViewDataBinding>() {

    private val mAdapter by lazy { BindingAdapter<OilPrice>(context,R.layout.rv_oil_price_item) }

    override fun getLayoutId(): Int {
        return R.layout.oil_price_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        registerMessageEvent {
            ToastUtils.showToast(this,it)
        }

        with(recyclerView){
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
        if(!srl.isRefreshing){
            srl.isRefreshing = true
        }
    }

    override fun hideLoading() {
        srl.isRefreshing = false
    }
}