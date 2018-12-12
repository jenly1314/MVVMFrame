package com.king.frame.mvvmframe.base;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

/**
 * 用来规范{@link BaseActivity} 和{@link BaseFragment} 风格。
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface IView<VM extends ViewModel> {

    /**
     * 根布局id
     * @return
     */
    @LayoutRes int getLayoutId();

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用DataBinding
     * @return
     */
    boolean isBinding();

    /**
     * 创建ViewModel
     * @return
     */
    VM createViewModel();

}
