package com.king.mvvmframe.app.likepoetry;

import android.app.Application;
import android.text.TextUtils;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.mvvmframe.R;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.SearchHistory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class LikePoetryViewModel extends BaseViewModel<LikePoetryModel> {

    private MediatorLiveData<List<PoetryInfo>> poetryLiveData = new MediatorLiveData<>();

    private MediatorLiveData<List<SearchHistory>> searchHistoryLiveData = new MediatorLiveData<>();

    private LiveData<Resource<List<PoetryInfo>>> poetrySource;

    private LiveData<List<SearchHistory>> searchHistorySource;

    @Inject
    public LikePoetryViewModel(@NonNull Application application, LikePoetryModel model) {
        super(application, model);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getSearchHistory(10);
    }

    /**
     * 模糊搜索诗词
     * @param name 搜索诗词名、诗词内容、诗词作者
     */
    public void getLikePoetry(String name){
        if(poetrySource!=null){
            poetryLiveData.removeSource(poetrySource);
        }
        poetrySource = getModel().getLikePoetry(name);
        poetryLiveData.addSource(poetrySource, resource -> {
            updateStatus(resource.status);
            if(resource.isSuccess()){//成功
                poetryLiveData.postValue(resource.data);
            }else if(resource.isFailure()){//失败
                if(!TextUtils.isEmpty(resource.message)){
                    postMessage(resource.message);
                }else{
                    postMessage(R.string.result_failure);
                }
            }else if(resource.isError()){
                postMessage(resource.error.getMessage());
            }
        });
    }

    /**
     * 获取搜索历史
     * @param count
     */
    public void getSearchHistory(int count){
        if(searchHistorySource!=null){
            searchHistoryLiveData.removeSource(searchHistorySource);
        }
        searchHistorySource = getModel().getSearchHistory(count);
        searchHistoryLiveData.addSource(searchHistorySource,searchHistoryList ->
                searchHistoryLiveData.setValue(searchHistoryList)
        );
    }

    /**
     * 清空历史
     */
    public void deleteAllHistory(){
        getModel().deleteAllHistory();
    }

    public LiveData<List<PoetryInfo>> getPoetryLiveData(){
        return poetryLiveData;
    }

    public LiveData<List<SearchHistory>> getSearchHistoryLiveData(){
        return searchHistoryLiveData;
    }
}
