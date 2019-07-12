package com.king.frame.mvvmframe.di.component;

import android.databinding.ViewDataBinding;

import com.king.frame.mvvmframe.base.BaseFragment;
import com.king.frame.mvvmframe.base.BaseViewModel;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Subcomponent(modules = {AndroidSupportInjectionModule.class})
public interface BaseFragmentSubcomponent extends AndroidInjector<BaseFragment<BaseViewModel, ViewDataBinding>> {


    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment<BaseViewModel,ViewDataBinding>>{

    }
}
