package com.king.mvvmframe;

import android.app.Application;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class TestViewModel extends BaseViewModel<BaseModel> {
    public TestViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }
}
