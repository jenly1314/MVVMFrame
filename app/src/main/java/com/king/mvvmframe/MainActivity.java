package com.king.mvvmframe;

import android.os.Bundle;
import android.view.View;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.king.mvvmframe.app.dictionary.DictionaryActivity;
import com.king.mvvmframe.app.oil.OilPriceActivity;
import com.king.mvvmframe.app.oil.OilPriceModel;
import com.king.mvvmframe.app.oil.OilPriceViewModel;
import com.king.mvvmframe.app.oil.lite.OilPriceLiteActivity;
import com.king.mvvmframe.app.oil.lite.OilPriceLiteViewModel;
import com.king.mvvmframe.databinding.MainActivityBinding;

import androidx.annotation.Nullable;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

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
 * Java 示例
 *
 * MVVM 精简分层示例 （适用于常规时）
 * 主要示例核心请查看 {@link OilPriceLiteActivity} 和 {@link OilPriceLiteViewModel}
 *
 * MVVM 标准分层示例 （适用于逻辑比较复杂时，分层更细）
 * 主要示例核心请查看 {@link OilPriceActivity} ， {@link OilPriceViewModel} 和 {@link OilPriceModel}
 *
 * Kotlin 示例 请查看 [sample-kotlin]
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
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


    @Override
    public boolean isBinding() {
        return false;
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn1:
                startActivity(OilPriceActivity.class);
                break;
            case R.id.btn2:
                startActivity(OilPriceLiteActivity.class);
                break;
            case R.id.btn3:
                startActivity(DictionaryActivity.class);
                break;
            case R.id.btn4:
                getViewModel().getHotCities();
                break;
        }
    }
}
