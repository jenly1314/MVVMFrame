//package com.king.frame.mvvmframe.di.module;
//
//
//import com.king.frame.mvvmframe.base.BaseModel;
//import com.king.frame.mvvmframe.base.BaseViewModel;
//import com.king.frame.mvvmframe.base.DataViewModel;
//import com.king.frame.mvvmframe.data.DataRepository;
//import com.king.frame.mvvmframe.data.IDataRepository;
//import com.king.frame.mvvmframe.di.scope.ViewModelKey;
//
//import androidx.lifecycle.ViewModel;
//import dagger.Binds;
//import dagger.Module;
//import dagger.multibindings.IntoMap;
//
///**
// * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
// */
//@Module(includes = {ConfigModule.class,HttpModule.class,ViewModelFactoryModule.class})
//public abstract class AppModule {
//
//    @Binds
//    abstract IDataRepository bindDataRepository(DataRepository dataRepository);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(BaseViewModel.class)
//    abstract ViewModel bindBaseViewModel(BaseViewModel<? extends BaseModel> viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(DataViewModel.class)
//    abstract ViewModel bindDataViewModel(DataViewModel viewModel);
//
//
//
//}
