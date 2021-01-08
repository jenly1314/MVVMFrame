package com.king.mvvmframe.example.app.viewmodel;

import android.app.Application;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.mvvmframe.example.app.model.MainModel;


import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;

/**
 * ViewModel例子
 * 继承{@link BaseViewModel}或其子类并且在构造函数加上@ViewModelInject注解
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class MainViewModel extends BaseViewModel<MainModel> {

    @ViewModelInject
    public MainViewModel(@NonNull Application application, MainModel model) {
        super(application, model);
    }
}
