package com.king.mvvmframe.example;

import android.os.Bundle;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.frame.mvvmframe.base.BaseViewModel;
import com.king.mvvmframe.example.app.viewmodel.MainViewModel;
import com.king.mvvmframe.example.bean.Bean;
import com.king.mvvmframe.example.databinding.MainActivityBinding;

import androidx.annotation.Nullable;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * Hilt大幅简化了Dagger2的用法，使得我们不用通过@Component注解去编写桥接层的逻辑，但是也因此限定了注入功能只能从几个Android固定的入口点开始，
 * Hilt一共支持6个入口点，分别是：
 * Application
 * Activity
 * Fragment
 * View
 * Service
 * BroadcastReceiver
 * 其中，只有Application这个入口点是使用@HiltAndroidApp注解来声明的，其他的所有入口点，都是用@AndroidEntryPoint注解来声明
 *
 * @example Application
 * //-------------------------
 *    @HiltAndroidApp
 *    public class YourApplication extends Application {
 *
 *    }
 * //-------------------------
 *
 * @example Activity
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourActivity extends BaseActivity {
 *
 *    }
 * //-------------------------
 *
 * @example Fragment
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourFragment extends BaseFragment {
 *
 *    }
 * //-------------------------
 *
 * example只是一个简单的MVVM分层的例子，为了演示通过MVVMFrame快速构建一个项目，如需查看带有逻辑的MVVM示例，请查看app中的演示demo
 * 简单示例，为了展示例子，写了个空的{@link MainViewModel}，如果没什么逻辑你也可以直接使用{@link BaseViewModel}
 *
 * 加上@AndroidEntryPoint注解
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
public class MainActivity extends BaseActivity<MainViewModel, MainActivityBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getViewDataBinding().setBean(new Bean("1","MVVMFrame"));

    }
}
