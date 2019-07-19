package com.king.frame.mvvmframe.di.component;

import com.king.frame.mvvmframe.base.BaseDialogFragment;
import com.king.frame.mvvmframe.base.BaseViewModel;

import androidx.databinding.ViewDataBinding;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseDialogFragmentSubcomponent extends AndroidInjector<BaseDialogFragment<BaseViewModel, ViewDataBinding>> {

    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<BaseDialogFragment<BaseViewModel, ViewDataBinding>> {

    }

}
