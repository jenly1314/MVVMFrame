package com.king.frame.mvvmframe.di.component;

import com.king.frame.mvvmframe.base.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivitySubcomponent extends AndroidInjector<BaseActivity> {


    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity>{

    }
}
