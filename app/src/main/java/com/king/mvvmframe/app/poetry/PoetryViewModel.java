package com.king.mvvmframe.app.poetry;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.mvvmframe.R;
import com.king.mvvmframe.bean.PoetryInfo;

import javax.inject.Inject;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryViewModel extends BaseViewModel<PoetryModel> {

    private MediatorLiveData<PoetryInfo> poetryLiveData = new MediatorLiveData<>();

    @Inject
    public PoetryViewModel(@NonNull Application application, PoetryModel model) {
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
        LiveData<Resource<PoetryInfo>> source = mModel.getPoetryInfo();
        poetryLiveData.addSource(source, poetryInfoResource -> {
            poetryLiveData.removeSource(source);
            poetryLiveData.addSource(source, resource -> {
                updateStatus(resource.status);
                if(resource.isSuccess()){//成功
                    poetryLiveData.setValue(resource.data);
                }else if(resource.isFailure()){//失败
                    if(!TextUtils.isEmpty(resource.message)){
                        sendMessage(resource.message);
                    }else{
                        sendMessage(R.string.result_failure);
                    }
                }
            });
        });

    }

    public LiveData<PoetryInfo> getPoetryLiveData(){
        return poetryLiveData;
    }
}
