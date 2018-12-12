package com.king.mvvmframe.di.component;

import com.king.frame.mvvmframe.di.component.AppComponent;
import com.king.frame.mvvmframe.di.scope.ApplicationScope;
import com.king.mvvmframe.App;
import com.king.mvvmframe.di.module.AppModule;

import dagger.Component;

/**
 * 依赖框架中的{@link AppComponent}
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ApplicationScope
@Component(dependencies = AppComponent.class,modules = {AppModule.class})
public interface ApplicationComponent {

    void inject(App app);

}
