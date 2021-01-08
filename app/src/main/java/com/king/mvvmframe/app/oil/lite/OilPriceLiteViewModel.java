package com.king.mvvmframe.app.oil.lite;

import android.app.Application;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.R;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.app.Constants;
import com.king.mvvmframe.bean.OilPrice;
import com.king.mvvmframe.bean.Result;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;

/**
 * 精简版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class OilPriceLiteViewModel extends DataViewModel {

    private MutableLiveData<List<OilPrice>> oilLiveData = new MutableLiveData<>();

    @ViewModelInject
    public OilPriceLiteViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getOilPriceInfo();
    }

    /**
     * 获取油价信息
     */
    public void getOilPriceInfo(){
        updateStatus(StatusEvent.Status.LOADING);
        getRetrofitService(ApiService.class)
                .getOilPriceInfo(Constants.OIL_PRICE_KEY)
                .enqueue(new ApiCallback<Result<List<OilPrice>>>() {
                    @Override
                    public void onResponse(Call<Result<List<OilPrice>>> call, Result<List<OilPrice>> result) {
                        if (result != null) {
                            if(result.isSuccess()){//成功
                                postUpdateStatus(StatusEvent.Status.SUCCESS);
                                oilLiveData.postValue(result.getData());
                                return;
                            }
                            postMessage(result.getMessage());
                        }else{
                            postMessage(R.string.result_failure);
                        }
                        postUpdateStatus(StatusEvent.Status.FAILURE);
                    }

                    @Override
                    public void onError(Call<Result<List<OilPrice>>> call, Throwable t) {
                        postUpdateStatus(StatusEvent.Status.ERROR);
                        postMessage(t.getMessage());
                    }
                });
    }

    public LiveData<List<OilPrice>> getOilLiveData(){
        return oilLiveData;
    }
}
