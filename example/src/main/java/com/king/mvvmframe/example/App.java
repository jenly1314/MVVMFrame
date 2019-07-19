package com.king.mvvmframe.example;

import android.content.Context;

import com.king.frame.mvvmframe.base.BaseApplication;
import com.king.mvvmframe.example.app.Constants;
import com.king.mvvmframe.example.di.component.ApplicationComponent;
import com.king.mvvmframe.example.di.component.DaggerApplicationComponent;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import timber.log.Timber;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class App extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        //---------------------------
        /**
         * 初始化日志打印，在此处初始化是因为MVVMFrame中的ApplicationDelegate是在BaseApplication中的attachBaseContext方法中初始化的，
         * 提前配置可方便查看打印框架中相关的配置信息
         */
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

        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .appComponent(getAppComponent())
                .build();
        //注入
        appComponent.inject(this);

    }
}
