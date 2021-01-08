package com.king.mvvmframe.app.oil;

import android.app.Application;
import android.text.TextUtils;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.mvvmframe.R;
import com.king.mvvmframe.bean.OilPrice;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class OilPriceViewModel extends BaseViewModel<OilPriceModel> {

    private MediatorLiveData<List<OilPrice>> oilLiveData = new MediatorLiveData<>();

    private LiveData<Resource<List<OilPrice>>> source;

    @ViewModelInject
    public OilPriceViewModel(@NonNull Application application, OilPriceModel model) {
        super(application, model);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        getOilPriceInfo();
    }

    /**
     * 查询油价信息
     */
    public void getOilPriceInfo(){
        if(source!=null){
            oilLiveData.removeSource(source);
        }
        source = getModel().getOilPriceInfo();
        oilLiveData.addSource(source, resource -> {
            updateStatus(resource.status);
            if(resource.isSuccess()){//成功
                oilLiveData.setValue(resource.data);
            }else if(resource.isFailure()){//失败
                if(!TextUtils.isEmpty(resource.message)){
                    sendMessage(resource.message);
                }else{
                    sendMessage(R.string.result_failure);
                }
            }else if(resource.isError()){//错误
                sendMessage(resource.error.getMessage());
            }
        });

    }

    public LiveData<List<OilPrice>> getOilLiveData(){
        return oilLiveData;
    }
}
