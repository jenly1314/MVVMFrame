package com.king.mvvmframe.example.app.viewmodel;

import android.app.Application;

import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.mvvmframe.example.app.model.MainModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;

/**
 * ViewModel例子
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class MainViewModel extends BaseViewModel<MainModel> {

    @Inject
    public MainViewModel(@NonNull Application application, MainModel model) {
        super(application, model);
    }
}
