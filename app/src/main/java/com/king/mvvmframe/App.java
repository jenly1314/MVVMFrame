package com.king.mvvmframe;

import android.content.Context;

import com.king.frame.mvvmframe.base.BaseApplication;
import com.king.mvvmframe.app.Constants;
import com.king.mvvmframe.di.component.ApplicationComponent;
import com.king.mvvmframe.di.component.DaggerApplicationComponent;
import com.king.retrofit.retrofithelper.RetrofitHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import timber.log.Timber;


/**
 *  MVVMFrame 框架基于Google官方的Architecture Components dependencies 构建，在使用MVVMFrame时，需遵循一些规范：
 *  1.你的项目中的Application中需初始化MVVMFrame框架相关信息，有两种方式处理：
 *      a.直接继承本类{@link BaseApplication}即可；
 *      b.如你的项目中的Application本身继承了其它第三方的Application，因为Java是单继承原因，导致没法继承本类，可参照{@link BaseApplication}类，
 *      将{@link BaseApplication}中相关代码复制到你项目的Application中，在相应的生命周期中调用即可。
 *
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
//        RetrofitHelper.getInstance().setBaseUrl("https://google.com");
        super.onCreate();

        //开始构建项目时，DaggerApplicationComponent类可能不存在，您需要执行Make Project才能生成，Make Project快捷键 Ctrl + F9
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .appComponent(getAppComponent())
                .build();
        //注入
        appComponent.inject(this);

        //TODO 动态新增多个 BaseUrl 示例
        //支持多个并且动态切换 BaseUrl
        RetrofitHelper.getInstance().putDomain(Constants.DOMAIN_JENLY,"https://jenly1314.github.io");
//        RetrofitHelper.getInstance().putDomain("Google","https://google.com");

    }


}
