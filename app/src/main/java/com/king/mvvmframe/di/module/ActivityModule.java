package com.king.mvvmframe.di.module;

import com.king.frame.mvvmframe.di.component.BaseActivitySubcomponent;
import com.king.mvvmframe.MainActivity;
import com.king.mvvmframe.app.likepoetry.LikePoetryActivity;
import com.king.mvvmframe.app.poetry.PoetryActivity;
import com.king.mvvmframe.app.poetrylite.PoetryLiteActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = BaseActivitySubcomponent.class)
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivity();

    @ContributesAndroidInjector
    abstract PoetryActivity contributesPoetryActivity();

    @ContributesAndroidInjector
    abstract PoetryLiteActivity contributesPoetryLiteActivity();

    @ContributesAndroidInjector
    abstract LikePoetryActivity contributesLikePoetryActivity();
}
