package com.king.frame.mvvmframe.base.delegate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.king.frame.mvvmframe.config.FrameConfigModule;
import com.king.frame.mvvmframe.base.IAppComponent;
import com.king.frame.mvvmframe.base.lifecycle.ApplicationLifecycle;
import com.king.frame.mvvmframe.config.ManifestParser;
import com.king.frame.mvvmframe.di.component.AppComponent;
import com.king.frame.mvvmframe.di.component.DaggerAppComponent;
import com.king.frame.mvvmframe.di.module.ConfigModule;
import com.king.frame.mvvmframe.util.Preconditions;

import java.util.List;

import timber.log.Timber;

/**
 * ApplicationDelegate 代理{@link Application} ,在 {@link Application}相应的生命周期{@link Application {@link #attachBaseContext(Context)}}，
 * {@link Application {@link #onCreate()}},{@link Application {@link #onTerminate()}} 方法中调用{@link ApplicationDelegate} 对应的方法，初始化框架基本的配置信息。
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class ApplicationDelegate implements ApplicationLifecycle,IAppComponent {

    private Application mApplication;

    private AppComponent mAppComponent;

    /**
     * 配置信息
     */
    private List<FrameConfigModule> mModules;

    public ApplicationDelegate(@NonNull Application application){
        this.mApplication = application;
    }

    /**
     * 获取配置信息
     * @param context
     * @param modules
     * @return {@link ConfigModule}
     */
    private ConfigModule getConfigModule(Context context,@NonNull List<FrameConfigModule> modules){
        ConfigModule.Builder builder = new ConfigModule.Builder();

        for (FrameConfigModule configModule: modules){
            if(configModule.isManifestParsingEnabled()){//如果启用则申请配置参数
                configModule.applyOptions(context,builder);
            }
        }
        return builder.build();

    }

    /**
     * 在{@link Application {@link #attachBaseContext(Context)}} 中执行,获取{@link #mModules}
     * @param base
     */
    @Override
    public void attachBaseContext(@NonNull Context base) {
        //通过配置文件解析配置信息
        Timber.d("attachBaseContext");
        mModules = new ManifestParser(base).parse();

    }

    /**
     * 在{@link Application {@link #onCreate()}} 中执行，初始化{@link #mAppComponent}
     */
    @Override
    public void onCreate() {
        Timber.d("onCreate");
        this.mAppComponent = DaggerAppComponent.builder()
                .application(mApplication)
                .configModule(getConfigModule(mApplication,mModules))
                .build();
        //注入
        this.mAppComponent.inject(this);

    }

    /**
     * 在{@link Application {@link #onTerminate()}} 中执行
     */
    @Override
    public void onTerminate() {
        this.mAppComponent = null;
        this.mModules = null;
        this.mApplication = null;
    }

    /**
     * 在{@link Application {@link #onLowMemory()}} 中执行,低内存的时候执行。（非必须调用）
     */
    @Override
    public void onLowMemory() {

    }

    /**
     * 在{@link Application {@link #onTrimMemory(int)}} 中执行，清理内存时执行。（非必须调用）
     */
    @Override
    public void onTrimMemory(int level) {

    }

    /**
     * 获取{@link AppComponent}
     * @return {@link #mAppComponent}
     */
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppComponent,"%s cannot be null",AppComponent.class.getName());
        return mAppComponent;
    }
}
