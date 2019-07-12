package com.king.mvvmframe.di.module;

import com.king.frame.mvvmframe.di.component.BaseDialogFragmentSubcomponent;
import com.king.frame.mvvmframe.di.scope.FragmentScope;
import com.king.mvvmframe.app.likepoetry.PoetryInfoDialogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = BaseDialogFragmentSubcomponent.class)
public abstract class DialogFragmentModule {

    @ContributesAndroidInjector
    abstract PoetryInfoDialogFragment contributesPoetryInfoDialogFragment();
}
