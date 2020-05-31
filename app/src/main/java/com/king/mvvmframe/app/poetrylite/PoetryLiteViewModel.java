package com.king.mvvmframe.app.poetrylite;

import android.app.Application;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.R;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;
import com.king.retrofit.retrofithelper.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;

/**
 * 精简版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryLiteViewModel extends DataViewModel {

    private MutableLiveData<List<PoetryInfo>> poetryLiveData = new MutableLiveData<>();

    @Inject
    public PoetryLiteViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getPoetryInfo();
    }

    /**
     * 获取诗词信息
     */
    public void getPoetryInfo(){
        updateStatus(StatusEvent.Status.LOADING);
        getRetrofitService(ApiService.class)
                .getRecommendPoetry()
                .enqueue(new ApiCallback<Result<List<PoetryInfo>>>() {
                    @Override
                    public void onResponse(Call<Result<List<PoetryInfo>>> call, Result<List<PoetryInfo>> result) {
                        if (result != null) {
                            if(result.isSuccess()){//成功
                                postUpdateStatus(StatusEvent.Status.SUCCESS);
                                poetryLiveData.postValue(result.getData());
                                return;
                            }
                            postMessage(result.getMessage());
                        }else{
                            postMessage(R.string.result_failure);
                        }
                        postUpdateStatus(StatusEvent.Status.FAILURE);
                    }

                    @Override
                    public void onError(Call<Result<List<PoetryInfo>>> call, Throwable t) {
                        postUpdateStatus(StatusEvent.Status.ERROR);
                        postMessage(t.getMessage());
                    }
                });
    }

    public LiveData<List<PoetryInfo>> getPoetryLiveData(){
        return poetryLiveData;
    }
}
