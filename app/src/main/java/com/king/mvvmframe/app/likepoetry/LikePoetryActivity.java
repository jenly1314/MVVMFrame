package com.king.mvvmframe.app.likepoetry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.king.base.adapter.HolderRecyclerAdapter;
import com.king.base.adapter.divider.DividerItemDecoration;
import com.king.base.util.StringUtils;
import com.king.base.util.SystemUtils;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.livedata.MessageEvent;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.mvvmframe.R;
import com.king.mvvmframe.app.adapter.BindingAdapter;
import com.king.mvvmframe.app.adapter.SearchHistoryAdapter;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.SearchHistory;
import com.king.mvvmframe.databinding.LikePoetryActivityBinding;
import com.king.mvvmframe.databinding.RvPoetryItemBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class LikePoetryActivity extends BaseActivity<LikePoetryViewModel,LikePoetryActivityBinding> {

    private SearchView searchView;

    private BindingAdapter<PoetryInfo,RvPoetryItemBinding> mAdapter;

    private List<PoetryInfo> listData;

    private SearchHistoryAdapter mSearchHistoryAdapter;

    private List<SearchHistory> listHistory;

    private String name;

    @Override
    public int getLayoutId() {
        return R.layout.like_poetry_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL,R.drawable.list_divider_8));

        listData = new ArrayList<>();
        mAdapter = new BindingAdapter<>(getContext(),listData,R.layout.rv_poetry_item);

        mBinding.recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position) -> {
            PoetryInfo data = mAdapter.getItem(position);
            showDialogFragment(PoetryInfoDialogFragment.newInstance(data));
        });

        listHistory = new ArrayList<>();
        mSearchHistoryAdapter = new SearchHistoryAdapter(getContext(),listHistory);
        mBinding.tflHistory.setAdapter(mSearchHistoryAdapter);

        mBinding.tflHistory.setOnTagClickListener((view, position, parent) -> {
            search(mSearchHistoryAdapter.getItem(position).getName());
            return true;
        });

        mBinding.setViewModel(mViewModel);

        mViewModel.getPoetryLiveData().observe(this, list -> {
            mAdapter.refreshData(list);
            if(list.size()==0){
                ToastUtils.showToast(getContext(),"没有相关结果");
            }else{
                searchView.clearFocus();
            }
        });

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

        mViewModel.getMessageEvent().observe(this, (MessageEvent.MessageObserver) message -> {
            Timber.d("message:%s" , message);
            ToastUtils.showToast(getContext(), message);

        });

        mViewModel.getSearchHistoryLiveData().observe(this,list ->{
            refreshSearchHistory(list);
        });
        mBinding.srl.setOnRefreshListener(()-> {
            search(name);
        });

    }

    private void search(String key){
        if(StringUtils.isNotBlank(key)){
            searchView.setQuery(key,true);
        }else{
            mBinding.srl.setRefreshing(false);
        }

    }

    private void refreshSearchHistory(List<SearchHistory> data){
        listHistory.clear();
        if(data!=null && data.size() > 0){
            listHistory.addAll(data);
            mBinding.ivDeleteHistory.setVisibility(View.VISIBLE);
        }else{
            mBinding.ivDeleteHistory.setVisibility(View.INVISIBLE);
        }
        mSearchHistoryAdapter.notifyDataChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchItem);
        //通过MenuItem得到SearchView
        searchView = (SearchView)searchItem.getActionView();
        searchView.setQueryHint("搜索诗词名、诗词内容、诗词作者");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(StringUtils.isNotBlank(s)){
                    name = s;
                    mViewModel.getLikePoetry(s);
                }else{
                    ToastUtils.showToast(getContext(),R.string.tips_search_poetry_);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void clickDelete(){
        ToastUtils.showToast(getContext(),"点击清空");
        mViewModel.deleteAllHistory();

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivDeleteHistory:
                clickDelete();
                break;
        }
    }
}
