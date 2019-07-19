package com.king.mvvmframe.example;

import android.os.Bundle;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.mvvmframe.example.app.viewmodel.MainViewModel;
import com.king.mvvmframe.example.bean.Bean;
import com.king.mvvmframe.example.databinding.MainActivityBinding;

import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity<BaseViewModel, MainActivityBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.setBean(new Bean("1","MVVMFrame"));

    }
}
