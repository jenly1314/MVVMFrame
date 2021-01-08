package com.king.mvvmframe.app.dictionary;

import android.app.Application;
import android.text.TextUtils;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.mvvmframe.R;
import com.king.mvvmframe.bean.DictionaryInfo;
import com.king.mvvmframe.bean.SearchHistory;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DictionaryViewModel extends BaseViewModel<DictionaryModel> {

    private MediatorLiveData<DictionaryInfo> dictionaryLiveData = new MediatorLiveData<>();

    private MediatorLiveData<List<SearchHistory>> searchHistoryLiveData = new MediatorLiveData<>();

    private LiveData<Resource<DictionaryInfo>> dictionarySource;

    private LiveData<List<SearchHistory>> searchHistorySource;

    @ViewModelInject
    public DictionaryViewModel(@NonNull Application application, DictionaryModel model) {
        super(application, model);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getSearchHistory(10);
    }

    /**
     * 获取字典信息
     * @param word 要查询的汉子
     */
    public void getDictionaryInfo(String word){
        if(dictionarySource != null){
            dictionaryLiveData.removeSource(dictionarySource);
        }
        dictionarySource = getModel().getDictionaryInfo(word);
        dictionaryLiveData.addSource(dictionarySource, resource -> {
            updateStatus(resource.status);
            if(resource.isSuccess()){//成功
                dictionaryLiveData.postValue(resource.data);
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

    public LiveData<DictionaryInfo> getDictionaryLiveData(){
        return dictionaryLiveData;
    }

    public LiveData<List<SearchHistory>> getSearchHistoryLiveData(){
        return searchHistoryLiveData;
    }
}
