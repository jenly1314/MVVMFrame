package com.king.mvvmframe;

import android.os.Bundle;
import android.view.View;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.mvvmframe.app.likepoetry.LikePoetryActivity;
import com.king.mvvmframe.app.poetry.PoetryActivity;
import com.king.mvvmframe.app.poetrylite.PoetryLiteActivity;
import com.king.mvvmframe.databinding.MainActivityBinding;

import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import timber.log.Timber;

/**
 * 因为目前Demo测试BaseUrl诗词已经不能用了，所以会请求不到数据，但是MVVM的相关结构与分层还是有参考意义的
 * REAMDE上面有MVVMFrame相关的开源项目，也可以参考。后续，考虑重新写个Demo
 */
public class MainActivity extends BaseActivity<MainViewModel,MainActivityBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        registerMessageEvent(message -> {
            Timber.d("message:%s" , message);
            ToastUtils.showToast(getContext(), message);
        });
        getViewModel().getLiveDataCities().observe(this, it -> {
            ToastUtils.showToast(getContext(), it.toString());
        });
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
            case R.id.btn4:
                getViewModel().getHotCities();
                break;
        }
    }
}
