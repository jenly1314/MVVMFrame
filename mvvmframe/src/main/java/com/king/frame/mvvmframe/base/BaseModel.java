package com.king.frame.mvvmframe.base;

import com.king.frame.mvvmframe.config.Constants;
import com.king.frame.mvvmframe.data.IDataRepository;

import java.lang.Class;
import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * <p>如果您继承使用了 BaseModel 或其子类，你需要参照如下方式在构造函数上添加 @Inject 注解
 *
 * <p>Example:
 * <pre>
 *    public class YourModel extends BaseModel {
 *        &#64;Inject
 *        public BaseModel(IDataRepository dataRepository){
 *            super(dataRepository);
 *        }
 *    }
 * </pre>
 *
 * <p>MVVM模式中的M (Model)层基类
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseModel implements IModel {

    private IDataRepository mDataRepository;

    @Inject
    public BaseModel(IDataRepository dataRepository){
        this.mDataRepository = dataRepository;
    }

    @Override
    public void onDestroy() {
        mDataRepository = null;
    }

    /**
     * 传入Class 获得{@link retrofit2.Retrofit#create(Class)} 对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    public <T> T getRetrofitService(Class<T> service){
        return mDataRepository.getRetrofitService(service);
    }


    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database){
        return getRoomDatabase(database,Constants.DEFAULT_DATABASE_NAME);
    }

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param dbName
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database, @Nullable String dbName){
        return mDataRepository.getRoomDatabase(database,dbName);
    }
}
