package com.king.mvvmframe.app.oil

import android.os.Bundle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.mvvmframe.R
import com.king.mvvmframe.app.adapter.BindingAdapter
import com.king.mvvmframe.bean.OilPrice
import com.king.mvvmframe.databinding.OilPriceActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@AndroidEntryPoint
class OilPriceActivity : BaseActivity<OilPriceViewModel, OilPriceActivityBinding>() {

    private val mAdapter by lazy {
        BindingAdapter<OilPrice>(getContext(), R.layout.rv_oil_price_item)
    }

    override fun getLayoutId(): Int {
        return R.layout.oil_price_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL,
                    R.drawable.list_divider_8
                )
            )
            adapter = mAdapter
        }

        mAdapter.setOnItemClickListener { _, position ->
            showToast(mAdapter.getItem(position)!!.city)
        }

        lifecycleScope.launch {
            viewModel.oilPriceFlow.flowWithLifecycle(lifecycle).collect {
                mAdapter.refreshData(it)
            }
        }

        binding.srl.setOnRefreshListener {
            viewModel.getOilPriceInfo()
        }

        viewModel.getOilPriceInfo()
    }

    override fun showLoading() {
        if (!binding.srl.isRefreshing) {
            super.showLoading()
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.srl.isRefreshing = false
    }
}