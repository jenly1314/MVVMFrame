package com.king.frame.mvvmframe.di.component;

import com.king.frame.mvvmframe.base.BaseFragment;
import com.king.frame.mvvmframe.base.BaseViewModel;

import androidx.databinding.ViewDataBinding;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseFragmentSubcomponent extends AndroidInjector<BaseFragment<BaseViewModel, ViewDataBinding>> {


    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment<BaseViewModel, ViewDataBinding>>{
        
    }
}
