package com.king.frame.mvvmframe.base;


import com.king.frame.mvvmframe.data.IDataRepository;

import javax.inject.Inject;

/**
 * 标准MVVM模式中的M (Model)层基类
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseModel implements IModel {

    private IDataRepository mDataRepository;

    @Inject
    public BaseModel(IDataRepository dataRepository){
        this.mDataRepository = dataRepository;
    }

    @Override
    public void onDestroy() {
        mDataRepository = null;
    }

    /**
     * 传入Class 获得{@link retrofit2.Retrofit#create(Class)} 对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    public <T> T getRetrofitService(Class<T> service){
        return mDataRepository.getRetrofitService(service);
    }
}
