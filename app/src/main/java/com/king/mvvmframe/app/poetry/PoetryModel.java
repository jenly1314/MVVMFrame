package com.king.mvvmframe.app.poetry;


import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.frame.mvvmframe.data.IDataRepository;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryModel extends BaseModel {

    private MutableLiveData<Resource<List<PoetryInfo>>> poetryLiveData = new MutableLiveData<>();

    @Inject
    public PoetryModel(IDataRepository dataRepository) {
        super(dataRepository);
    }

    /**
     * 获取诗词信息
     * @return
     */
    public LiveData<Resource<List<PoetryInfo>>> getPoetryInfo(){
        poetryLiveData.setValue(Resource.loading());
        getRetrofitService(ApiService.class)
                .getRecommendPoetry()
                .enqueue(new ApiCallback<Result<List<PoetryInfo>>>() {
                    @Override
                    public void onResponse(Call<Result<List<PoetryInfo>>> call, Result<List<PoetryInfo>> result) {
                        if (result != null) {
                            if(result.isSuccess()){
                                poetryLiveData.postValue(Resource.success(result.getData()));
                                return;
                            }
                            poetryLiveData.postValue(Resource.failure(result.getMessage()));
                        }
                        poetryLiveData.postValue(Resource.failure(null));
                    }

                    @Override
                    public void onError(Call<Result<List<PoetryInfo>>> call, Throwable t) {
                        poetryLiveData.postValue(Resource.error(t));
                    }
                });

        return poetryLiveData;
    }
}
