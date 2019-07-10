package com.king.mvvmframe;

import android.os.Bundle;
import android.view.View;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.mvvmframe.app.likepoetry.LikePoetryActivity;
import com.king.mvvmframe.app.poetry.PoetryActivity;
import com.king.mvvmframe.app.poetrylite.PoetryLiteActivity;
import com.king.mvvmframe.databinding.MainActivityBinding;

import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity<BaseViewModel,MainActivityBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn1:
                startActivity(PoetryActivity.class);
                break;
            case R.id.btn2:
                startActivity(PoetryLiteActivity.class);
                break;
            case R.id.btn3:
                startActivity(LikePoetryActivity.class);
                break;
        }
    }
}
