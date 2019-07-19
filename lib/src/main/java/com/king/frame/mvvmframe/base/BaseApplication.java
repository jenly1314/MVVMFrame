package com.king.frame.mvvmframe.base;

import android.app.Application;
import android.content.Context;

import com.king.frame.mvvmframe.base.delegate.ApplicationDelegate;
import com.king.frame.mvvmframe.di.component.AppComponent;
import com.king.frame.mvvmframe.util.Preconditions;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 *  MVVMFrame 框架基于Google官方的Architecture Components dependencies 构建，在使用MVVMFrame时，需遵循一些规范：
 *  1.你的项目中的Application中需初始化MVVMFrame框架相关信息，有两种方式处理：
 *      a.直接继承本类{@link BaseApplication}即可；
 *      b.如你的项目中的Application本身继承了其它第三方的Application，因为Java是单继承原因，导致没法继承本类，可参照本类，
 *      将{@link #mApplicationDelegate}相关代码复制到你项目的Application中，在相应的生命周期中调用即可。
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseApplication extends Application implements IAppComponent, HasAndroidInjector {

    /**
     * Application 代理 规避项目中集成了其它第三方类或其它原因，不能集成本类{@link BaseApplication}时，
     * 参照本类实现 {@link ApplicationDelegate} 即可初始化MVVMFrame框架配置信息。
     */
    private ApplicationDelegate mApplicationDelegate;

    /**
     * Dagger.Android 注入
     */
    @Inject
    DispatchingAndroidInjector<Object> mAndroidInjector;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApplicationDelegate = new ApplicationDelegate(this);
        mApplicationDelegate.attachBaseContext(base);

    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationDelegate.onCreate();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mApplicationDelegate.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mApplicationDelegate.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mApplicationDelegate.onTrimMemory(level);
    }

    /**
     * 通过{@link #mApplicationDelegate} 获取{@link AppComponent}
     * @return 返回 {@link #mApplicationDelegate {@link #getAppComponent()}}}
     */
    @Override
    public AppComponent getAppComponent(){
        Preconditions.checkNotNull(mApplicationDelegate,"%s cannot be null",ApplicationDelegate.class.getName());
     return mApplicationDelegate.getAppComponent();
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mAndroidInjector;
    }

}
