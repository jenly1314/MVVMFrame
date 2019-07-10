package com.king.mvvmframe.app.poetry;

import android.app.Application;
import android.text.TextUtils;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.mvvmframe.R;
import com.king.mvvmframe.bean.PoetryInfo;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryViewModel extends BaseViewModel<PoetryModel> {

    private MediatorLiveData<List<PoetryInfo>> poetryLiveData = new MediatorLiveData<>();

    private LiveData<Resource<List<PoetryInfo>>> source;

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
        if(source!=null){
            poetryLiveData.removeSource(source);
        }
        source = mModel.getPoetryInfo();
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

    }

    public LiveData<List<PoetryInfo>> getPoetryLiveData(){
        return poetryLiveData;
    }
}
