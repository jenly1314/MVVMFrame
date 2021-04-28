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

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;

/**
 * 精简版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
public class OilPriceLiteViewModel extends DataViewModel {

    private MutableLiveData<List<OilPrice>> oilLiveData = new MutableLiveData<>();

    @Inject
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
                                updateStatus(StatusEvent.Status.SUCCESS,true);
                                oilLiveData.postValue(result.getData());
                                return;
                            }
                            sendMessage(result.getMessage(),true);
                        }else{
                            sendMessage(R.string.result_failure,true);
                        }
                        sendMessage(StatusEvent.Status.FAILURE, true);
                    }

                    @Override
                    public void onError(Call<Result<List<OilPrice>>> call, Throwable t) {
                        updateStatus(StatusEvent.Status.ERROR,true);
                        sendMessage(t.getMessage(), true);
                    }
                });
    }

    public LiveData<List<OilPrice>> getOilLiveData(){
        return oilLiveData;
    }
}
