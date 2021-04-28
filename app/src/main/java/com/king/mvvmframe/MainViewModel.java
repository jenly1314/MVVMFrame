package com.king.mvvmframe;

import android.app.Application;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.api.ApiService;

import java.util.List;
import java.util.Map;


import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
public class MainViewModel extends DataViewModel {

    private MutableLiveData<List<Map<String,Object>>> liveDataCities = new MutableLiveData<>();

    @Inject
    public MainViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    public void getHotCities(){
        showLoading();
        getRetrofitService(ApiService.class).getHotCities().enqueue(new ApiCallback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, List<Map<String, Object>> result) {
                if (result != null) {
                    liveDataCities.postValue(result);
                }else{
                    sendMessage(R.string.result_failure,true);
                }
                hideLoading(true);
            }

            @Override
            public void onError(Call<List<Map<String, Object>>> call, Throwable t) {
                sendMessage(t.getMessage(),true);
                hideLoading(true);
            }
        });
    }

    public MutableLiveData<List<Map<String,Object>>> getLiveDataCities(){
        return liveDataCities;
    }
}
