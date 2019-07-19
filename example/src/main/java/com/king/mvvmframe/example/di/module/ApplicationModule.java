package com.king.mvvmframe.example.di.module;

import com.king.frame.mvvmframe.di.module.ViewModelFactoryModule;
import com.king.mvvmframe.example.di.component.ApplicationComponent;

import dagger.Module;

/**
 * Application模块：为{@link ApplicationComponent}提供注入的各个模块
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(includes = {ViewModelFactoryModule.class,ViewModelModule.class,ActivityModule.class})
public class ApplicationModule {

}
