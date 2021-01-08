package com.king.mvvmframe.app.dictionary;


import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.frame.mvvmframe.data.IDataRepository;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.app.Constants;
import com.king.mvvmframe.bean.DictionaryInfo;
import com.king.mvvmframe.bean.Result;
import com.king.mvvmframe.bean.SearchHistory;
import com.king.mvvmframe.dao.AppDatabase;
import com.king.mvvmframe.dao.SearchHistoryDao;

import java.lang.Class;
import java.lang.String;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DictionaryModel extends BaseModel {

    private MutableLiveData<Resource<DictionaryInfo>> dictionaryLiveData = new MutableLiveData<>();

    @Inject
    public DictionaryModel(IDataRepository dataRepository) {
        super(dataRepository);
    }

    /**
     * 获取字典信息
     * @param word 要查询的汉子
     * @return
     */
    public LiveData<Resource<DictionaryInfo>> getDictionaryInfo(@NonNull String word){
        dictionaryLiveData.setValue(Resource.loading());
        getRetrofitService(ApiService.class)
                .getDictionaryInfo(Constants.DICTIONARY_KEY,word)
                .enqueue(new ApiCallback<Result<DictionaryInfo>>() {
                    @Override
                    public void onResponse(Call<Result<DictionaryInfo>> call, Result<DictionaryInfo> result) {
                        if (result != null) {
                            if(result.isSuccess()){
                                dictionaryLiveData.setValue(Resource.success(result.getData()));
                                return;
                            }
                            dictionaryLiveData.setValue(Resource.failure(result.getMessage()));
                        }
                        dictionaryLiveData.setValue(Resource.failure(null));
                    }

                    @Override
                    public void onError(Call<Result<DictionaryInfo>> call, Throwable t) {
                        dictionaryLiveData.setValue(Resource.error(t));
                    }
                });
        //添加历史
        addHistory(word);
        return  dictionaryLiveData;
    }

    /**
     * 获取搜索历史 {@link SearchHistoryDao#getHistory(int)}
     * @param count
     * @return
     */
    public LiveData<List<SearchHistory>> getSearchHistory(int count){
        return getSearchHistoryDao().getHistory(count);
    }

    /**
     * 添加搜索历史 {@link SearchHistoryDao#insert(SearchHistory)}
     * @param name
     */
    public void addHistory(@NonNull String name){
        Observable.just(name)
                .subscribeOn(Schedulers.io())
                .subscribe(key ->
                        getSearchHistoryDao().insert(new SearchHistory(key))
                );

    }

    /**
     * 清空历史 {@link SearchHistoryDao#deleteAll()}
     */
    public void deleteAllHistory(){
        Observable.just(1).subscribeOn(Schedulers.io())
                .subscribe(integer ->
                    getSearchHistoryDao().deleteAll()
                );
    }


    /**
     * 获取SearchHistoryDao {@link BaseModel#getRoomDatabase(Class, String)} #{@link AppDatabase#searchHistoryDao()}
     * @return {@link SearchHistoryDao}
     */
    public SearchHistoryDao getSearchHistoryDao(){
        return getRoomDatabase(AppDatabase.class).searchHistoryDao();
    }
}
