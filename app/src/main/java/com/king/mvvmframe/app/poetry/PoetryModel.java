package com.king.mvvmframe.app.poetry;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.frame.mvvmframe.data.IDataRepository;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryModel extends BaseModel {

    private MutableLiveData<Resource<PoetryInfo>> poetryLiveData = new MutableLiveData<>();

    @Inject
    public PoetryModel(IDataRepository dataRepository) {
        super(dataRepository);
    }

    /**
     * 获取诗词信息
     * @return
     */
    public LiveData<Resource<PoetryInfo>> getPoetryInfo(){
        poetryLiveData.setValue(Resource.loading());
        getRetrofitService(ApiService.class)
                .getRecommendPoetry()
                .enqueue(new ApiCallback<Result<PoetryInfo>>() {
                    @Override
                    public void onResponse(Call<Result<PoetryInfo>> call, Result<PoetryInfo> result) {
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
                    public void onError(Call<Result<PoetryInfo>> call, Throwable t) {
                        poetryLiveData.setValue(Resource.error(t));
                    }
                });

        return poetryLiveData;
    }
}
