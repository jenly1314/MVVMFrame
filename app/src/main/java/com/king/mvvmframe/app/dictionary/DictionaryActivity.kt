package com.king.mvvmframe.app.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.SearchAutoComplete
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.base.util.StringUtils
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.mvvmframe.R
import com.king.mvvmframe.app.adapter.BindingAdapter
import com.king.mvvmframe.app.adapter.SearchHistoryAdapter
import com.king.mvvmframe.data.model.SearchHistory
import com.king.mvvmframe.databinding.DictionaryActivityBinding
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
class DictionaryActivity : BaseActivity<DictionaryViewModel, DictionaryActivityBinding>() {

    private var searchView: SearchView? = null

    private val mAdapter by lazy {
        BindingAdapter<String>(getContext(), R.layout.rv_dictionary_item)
    }

    private val listHistory = mutableListOf<SearchHistory>()

    private val mSearchHistoryAdapter by lazy {
        SearchHistoryAdapter(listHistory)
    }

    private var word: String = ""

    override fun getLayoutId(): Int {
        return R.layout.dictionary_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(getContext())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = mAdapter

        binding.tflHistory.adapter = mSearchHistoryAdapter

        binding.tflHistory.setOnTagClickListener { _, position, _ ->
            search(mSearchHistoryAdapter.getItem(position).word)
            true
        }

        binding.srl.setOnRefreshListener {
            search(word)
        }

        lifecycleScope.launch {
            viewModel.dictionaryFlow.flowWithLifecycle(lifecycle).collect {
                mAdapter.refreshData(it?.xiangjie)
            }
        }

        lifecycleScope.launch {
            viewModel.getSearchHistoryFlow().flowWithLifecycle(lifecycle).collect {
                listHistory.clear()
                listHistory.addAll(it)
                mSearchHistoryAdapter.notifyDataChanged()
            }
        }
    }

    private fun search(key: String) {
        if (StringUtils.isNotBlank(key)) {
            searchView?.setQuery(key, true)
        } else {
            binding.srl.isRefreshing = false
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view_menu, menu)
        val searchItem = menu.findItem(R.id.searchItem)
        //通过MenuItem得到SearchView
        searchView = searchItem.actionView as SearchView?
        val searchSrcTextView = searchView?.findViewById<SearchAutoComplete>(R.id.search_src_text)
        searchSrcTextView?.also {
            it.filters = arrayOf<InputFilter>(LengthFilter(1))
        }
        searchView?.queryHint = getString(R.string.word_search_hint)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if (StringUtils.isNotBlank(s)) {
                    word = s
                    viewModel.getDictionaryInfo(word)
                    searchView?.clearFocus()
                } else {
                    showToast(getString(R.string.tips_search_content))
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
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

    private fun clearSearchHistory() {
        viewModel.clearSearchHistory()
        mAdapter.refreshData(null)
        word = ""
        searchView?.setQuery(word, false)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.ivDeleteHistory -> clearSearchHistory()
        }
    }
}
