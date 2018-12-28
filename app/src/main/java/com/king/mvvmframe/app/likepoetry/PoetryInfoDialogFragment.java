package com.king.mvvmframe.app.likepoetry;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseDialogFragment;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.mvvmframe.R;
import com.king.mvvmframe.app.Constants;
import com.king.mvvmframe.bean.PoetryInfo;
import com.king.mvvmframe.databinding.PoetryInfoDialogFragmentBinding;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryInfoDialogFragment extends BaseDialogFragment<DataViewModel,PoetryInfoDialogFragmentBinding> {

    public static PoetryInfoDialogFragment newInstance(PoetryInfo data) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_POETRY_INFO,data);
        PoetryInfoDialogFragment fragment = new PoetryInfoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.poetry_info_dialog_fragment;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        PoetryInfo data = getArguments().getParcelable(Constants.KEY_POETRY_INFO);
        mBinding.setData(data);

        mBinding.btnClose.setOnClickListener(view -> getDialog().dismiss());
    }
}
