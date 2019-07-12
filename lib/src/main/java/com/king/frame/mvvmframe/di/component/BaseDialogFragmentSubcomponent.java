package com.king.frame.mvvmframe.di.component;

import android.databinding.ViewDataBinding;

import com.king.frame.mvvmframe.base.BaseDialogFragment;
import com.king.frame.mvvmframe.base.BaseViewModel;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Subcomponent(modules = {AndroidSupportInjectionModule.class})
public interface BaseDialogFragmentSubcomponent extends AndroidInjector<BaseDialogFragment<BaseViewModel, ViewDataBinding>> {


    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseDialogFragment<BaseViewModel,ViewDataBinding>>{

    }
}
