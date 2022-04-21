package com.king.frame.mvvmframe.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * 用来规范 {@link BaseActivity} 和 {@link BaseFragment} 风格。
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface IView<VM extends IViewModel> {

    /**
     * 根布局id
     * @return
     */
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用 DataBinding
     * @return
     */
    boolean isBinding();

    /**
     * 创建 ViewModel
     * @return
     */
    VM createViewModel();

}
