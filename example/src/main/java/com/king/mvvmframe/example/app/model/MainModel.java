package com.king.mvvmframe.example.app.model;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.data.IDataRepository;

import javax.inject.Inject;

/**
 * Model例子
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class MainModel extends BaseModel {

    @Inject
    public MainModel(IDataRepository dataRepository) {
        super(dataRepository);
    }
}
