package com.king.frame.mvvmframe.base;

import android.app.Application;

import java.lang.Class;
import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * 如果您继承使用了DataViewModel或其子类，你需要参照如下方式在构造函数上添加@ViewModelInject注解
 *
 * @example DataViewModel
 * //-------------------------
 *    public class YourViewModel extends DataViewModel {
 *        @ViewModelInject
 *        public DataViewModel(@NonNull Application application, BaseModel model) {
 *            super(application, model);
 *        }
 *    }
 * //-------------------------
 *
 *
 * 默认提供{@link BaseModel#getRetrofitService}的功能，当ViewModel和Model数据比较简单时可使用本类，弱化Model层。
 * 如果ViewModel或Model层里面逻辑比较复杂请尽量使用继承{@link BaseViewModel} 和{@link BaseModel}进行分层。
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DataViewModel extends BaseViewModel<BaseModel> {

    @ViewModelInject
    public DataViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    /**
     * 传入Class 获得{@link retrofit2.Retrofit#create(Class)} 对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    public <T> T getRetrofitService(Class<T> service){
        return getModel().getRetrofitService(service);
    }

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database){
        return getRoomDatabase(database, null);
    }
    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param dbName
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database, @Nullable String dbName){
        return getModel().getRoomDatabase(database,dbName);
    }
}
