package com.king.mvvmframe.example.di.component;

import com.king.frame.mvvmframe.di.component.AppComponent;
import com.king.frame.mvvmframe.di.scope.ApplicationScope;
import com.king.mvvmframe.example.App;
import com.king.mvvmframe.example.di.module.ApplicationModule;

import dagger.Component;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ApplicationScope
@Component(dependencies = AppComponent.class,modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(App app);
}
