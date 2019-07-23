package com.king.frame.mvvmframe.di.component;

import android.app.Application;

import com.king.frame.mvvmframe.base.delegate.ApplicationDelegate;
import com.king.frame.mvvmframe.data.IDataRepository;
import com.king.frame.mvvmframe.di.module.AppModule;
import com.king.frame.mvvmframe.di.module.ConfigModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(ApplicationDelegate applicationDelegate);

    Application getApplication();

    IDataRepository getDataRepository();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        Builder configModule(ConfigModule configModule);

        AppComponent build();
    }

}
