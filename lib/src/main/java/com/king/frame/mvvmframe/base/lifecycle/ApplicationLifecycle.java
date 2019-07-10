package com.king.frame.mvvmframe.base.lifecycle;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 代理{@link Application} 的生命周期, 实现类见{@link com.king.frame.mvvmframe.base.delegate.ApplicationDelegate}
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface ApplicationLifecycle {

    /**
     * 在{@link Application#attachBaseContext(Context)} 中执行
     * @param base
     */
    void attachBaseContext(@NonNull Context base);
    /**
     * 在{@link Application#onCreate()} 中执行
     */
    void onCreate();
    /**
     * 在{@link Application#onTerminate()} 中执行
     */
    void onTerminate();
    /**
     * 在{@link Application#onLowMemory()} 中执行
     */
    void onLowMemory();
    /**
     * 在{@link Application#onTrimMemory(int)} 中执行
     */
    void onTrimMemory(int level);
}
