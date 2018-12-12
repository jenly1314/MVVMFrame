package com.king.frame.mvvmframe.di.component;

import com.king.frame.mvvmframe.base.BaseDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Subcomponent(modules = {AndroidSupportInjectionModule.class})
public interface BaseDialogFragmentSubcomponent extends AndroidInjector<BaseDialogFragment> {


    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseDialogFragment>{

    }
}
