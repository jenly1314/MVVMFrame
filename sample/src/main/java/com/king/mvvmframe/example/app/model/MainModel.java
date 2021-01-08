package com.king.mvvmframe.example.app.model;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.data.IDataRepository;

import javax.inject.Inject;

/**
 * Model例子
 * 继承{@link #BaseModel}或其子类并且在构造函数加上@Inject
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class MainModel extends BaseModel {

    @Inject
    public MainModel(IDataRepository dataRepository) {
        super(dataRepository);
    }
}
