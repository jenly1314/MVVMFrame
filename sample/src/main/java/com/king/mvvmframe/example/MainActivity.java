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
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * <p>Hilt 大幅简化了 Dagger2 的用法，使得我们不用通过 @Component 注解去编写桥接层的逻辑，但是也因此限定了注入功能只能从几个 Android 固定的入口点开始，
 * <p>Hilt 一共支持6个入口点，分别是：
 * <p>Application
 * <p>Activity
 * <p>Fragment
 * <p>View
 * <p>Service
 * <p>BroadcastReceiver
 *
 * //===================================================//
 * <pre>
 * 其中，只有 Application 这个入口点是使用 @HiltAndroidApp 注解来声明，示例如下
 *
 *
 * &#64;example: Application
 * //-------------------------
 *    &#64;HiltAndroidApp
 *    public class YourApplication extends Application {
 *
 *    }
 * //-------------------------
 *
 * //---------------------------------------------------//
 *
 * 其他的所有入口点，都是用@AndroidEntryPoint注解来声明，示例如下
 *
 * Example: Activity
 * //-------------------------
 *    &#64;AndroidEntryPoint
 *    public class YourActivity extends BaseActivity {
 *
 *    }
 * //-------------------------
 *
 * Example: Fragment
 * //-------------------------
 *    &#64;AndroidEntryPoint
 *    public class YourFragment extends BaseFragment {
 *
 *    }
 * //-------------------------
 *
 * Example: Service
 * //-------------------------
 *    &#64;AndroidEntryPoint
 *    public class YourService extends BaseService {
 *
 *    }
 * //-------------------------
 *
 * Example: BroadcastReceiver
 * //-------------------------
 *    &#64;AndroidEntryPoint
 *    public class YourBroadcastReceiver extends BaseBroadcastReceiver {
 *
 *    }
 * //-------------------------
 * </pre>
 * //===================================================//
 *
 * <p> example 只是一个简单的 MVVM 分层的例子，为了演示通过 MVVMFrame 快速构建一个项目，如需查看带有逻辑的 MVVM 示例，请查看 app 中的演示 demo
 * 简单示例，为了展示例子，写了个空的 {@link MainViewModel}，如果没什么逻辑你也可以直接使用 {@link BaseViewModel}
 * <p>
 * <p>Java 示例 请查看 [sample-kotlin]
 * <p>
 * <p>Kotlin 示例 请查看 [sample-kotlin]
 * <p>
 *
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
