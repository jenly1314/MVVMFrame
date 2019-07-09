package com.king.mvvmframe.app.poetrylite;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.R;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.bean.Result;

import java.util.List;

import javax.inject.Inject;

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
                                updateStatus(StatusEvent.Status.SUCCESS);
                                poetryLiveData.setValue(result.getData());
                                return;
                            }
                            sendMessage(result.getMessage());
                        }else{
                            sendMessage(R.string.result_failure);
                        }
                        updateStatus(StatusEvent.Status.FAILURE);
                    }

                    @Override
                    public void onError(Call<Result<List<PoetryInfo>>> call, Throwable t) {
                        updateStatus(StatusEvent.Status.ERROR);
                        sendMessage(t.getMessage());
                    }
                });
    }

    public LiveData<List<PoetryInfo>> getPoetryLiveData(){
        return poetryLiveData;
    }
}
