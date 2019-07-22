package com.king.mvvmframe.app.likepoetry;


import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.frame.mvvmframe.data.IDataRepository;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;
import com.king.mvvmframe.bean.SearchHistory;
import com.king.mvvmframe.dao.AppDatabase;
import com.king.mvvmframe.dao.SearchHistoryDao;

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
public class LikePoetryModel extends BaseModel {

    private MutableLiveData<Resource<List<PoetryInfo>>> poetryLiveData = new MutableLiveData<>();

    @Inject
    public LikePoetryModel(IDataRepository dataRepository) {
        super(dataRepository);
    }

    /**
     * 模糊搜索诗词
     * @param keyword 搜索诗词名、诗词内容、诗词作者
     * @return
     */
    public LiveData<Resource<List<PoetryInfo>>> getLikePoetry(@NonNull String keyword){
        poetryLiveData.setValue(Resource.loading());
        getRetrofitService(ApiService.class)
                .searchPoetry(keyword,1)
                .enqueue(new ApiCallback<Result<List<PoetryInfo>>>() {
                    @Override
                    public void onResponse(Call<Result<List<PoetryInfo>>> call, Result<List<PoetryInfo>> result) {
                        if (result != null) {
                            if(result.isSuccess()){
                                poetryLiveData.setValue(Resource.success(result.getData()));
                                return;
                            }
                            poetryLiveData.setValue(Resource.failure(result.getMessage()));
                        }
                        poetryLiveData.setValue(Resource.failure(null));
                    }

                    @Override
                    public void onError(Call<Result<List<PoetryInfo>>> call, Throwable t) {
                        poetryLiveData.setValue(Resource.error(t));
                    }
                });
        //添加历史
        addHistory(keyword);
        return  poetryLiveData;
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
