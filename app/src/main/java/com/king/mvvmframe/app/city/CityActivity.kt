package com.king.mvvmframe.app.city

import android.os.Bundle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.mvvmframe.R
import com.king.mvvmframe.app.adapter.BindingAdapter
import com.king.mvvmframe.bean.City
import com.king.mvvmframe.databinding.CityActivityBinding
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
class CityActivity : BaseActivity<CityViewModel, CityActivityBinding>() {

    private val mAdapter by lazy {
        BindingAdapter<City>(getContext(), R.layout.rv_city_item)
    }

    override fun getLayoutId(): Int {
        return R.layout.city_activity
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
            showToast(mAdapter.getItem(position)!!.name)
        }

        lifecycleScope.launch {
            viewModel.cityFlow.flowWithLifecycle(lifecycle).collect {
                mAdapter.refreshData(it)
            }
        }

        binding.srl.setOnRefreshListener {
            viewModel.getHotCities()
        }

        viewModel.getHotCities()
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