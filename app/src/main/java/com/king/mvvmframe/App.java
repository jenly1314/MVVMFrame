package com.king.mvvmframe;

import android.app.Application;

import com.king.mvvmframe.app.Constants;
import com.king.retrofit.retrofithelper.RetrofitHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * 你需要参照如下方式添加@HiltAndroidApp注解
 *
 * @example Application
 * //-------------------------
 *    @HiltAndroidApp
 *    public class YourApplication extends Application {
 *
 *    }
 * //-------------------------
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltAndroidApp
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        //------------------------------
        //设置BaseUrl
//        RetrofitHelper.getInstance().setBaseUrl(Constants.BASE_URL);
        //设置动态BaseUrl
        RetrofitHelper.getInstance().putDomain(Constants.DOMAIN_DICTIONARY,Constants.DICTIONARY_BASE_URL);
        RetrofitHelper.getInstance().putDomain(Constants.DOMAIN_JENLY,"https://jenly1314.gitlab.io");
    }


    private void initLogger(){
        //初始化日志打印
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .methodOffset(5)
                .tag(Constants.TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Timber.plant(new Timber.DebugTree() {
            @Override protected void log(int priority, String tag, String message, Throwable t) {
                if(BuildConfig.DEBUG){
                    Logger.log(priority, tag, message, t);
                }
            }
        });
    }

}
