package com.king.mvvmframe.example;

import android.os.Bundle;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.mvvmframe.example.app.viewmodel.MainViewModel;
import com.king.mvvmframe.example.bean.Bean;
import com.king.mvvmframe.example.databinding.MainActivityBinding;

import androidx.annotation.Nullable;

/**
 * 简单示例，为了展示例子，写了个空的{@link MainViewModel}，如果没什么逻辑你也可以直接使用{@link BaseViewModel}
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class MainActivity extends BaseActivity<MainViewModel, MainActivityBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.setBean(new Bean("1","MVVMFrame"));

    }
}
