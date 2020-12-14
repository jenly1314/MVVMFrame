package com.king.mvvmframe.example.di.module;

import com.king.frame.mvvmframe.di.scope.ViewModelKey;
import com.king.mvvmframe.example.app.viewmodel.MainViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * ViewModel模块统一管理：通过{@link Binds}和{@link ViewModelKey}绑定关联对应的ViewModel
 * ViewModelModule 例子
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
