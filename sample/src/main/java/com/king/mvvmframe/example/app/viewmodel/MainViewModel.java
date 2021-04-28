package com.king.mvvmframe.example.app.viewmodel;

import android.app.Application;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.mvvmframe.example.app.model.MainModel;


import javax.inject.Inject;

import androidx.annotation.NonNull;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel例子
 * 继承{@link BaseViewModel}或其子类并且在构造函数加上@HiltViewModel注解
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
public class MainViewModel extends BaseViewModel<MainModel> {

    @Inject
    public MainViewModel(@NonNull Application application, MainModel model) {
        super(application, model);
    }
}
