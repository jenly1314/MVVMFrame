package com.king.frame.mvvmframe.di.component;

import com.king.frame.mvvmframe.base.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Subcomponent(modules = {AndroidSupportInjectionModule.class})
public interface BaseFragmentSubcomponent extends AndroidInjector<BaseFragment> {


    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment>{

    }
}
