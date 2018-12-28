package com.king.frame.mvvmframe.base;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.king.frame.mvvmframe.R;
import com.king.frame.mvvmframe.base.livedata.MessageEvent;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class BaseActivity<VM extends BaseViewModel,VDB extends ViewDataBinding> extends AppCompatActivity implements IView<VM>, BaseNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    protected VM mViewModel;

    protected VDB mBinding;

    protected static final float DEFAULT_WIDTH_RATIO = 0.85f;

    private Dialog mDialog;

    private Dialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Dagger.Android Activity 注入
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        if(isBinding()){
            mBinding = DataBindingUtil.setContentView(this,getLayoutId());
        }else{
            setContentView(getLayoutId());
        }
        initViewModel();
        initData(savedInstanceState);
    }

    /**
     * 初始化{@link #mViewModel}
     */
    private void initViewModel(){
        mViewModel = createViewModel();
        if (mViewModel == null) {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Class<VM> modelClass = (Class<VM>) ((ParameterizedType) type).getActualTypeArguments()[0];
                mViewModel = getViewModel(modelClass);
                getLifecycle().addObserver(mViewModel);
                registerLoadingEvent();
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mViewModel!=null){
            getLifecycle().removeObserver(mViewModel);
        }
        mViewModel = null;

        if(mBinding!=null){
            mBinding.unbind();
        }
    }

    /**
     * 注册状态加载事件
     */
    protected void registerLoadingEvent(){
        mViewModel.mLoadingEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if(isLoading){
                    showLoading();
                }else{
                    hideLoading();
                }
            }
        });
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    /**
     * 注册消息事件
     */
    protected void registerMessageEvent(@NonNull MessageEvent.MessageObserver observer){
        mViewModel.getMessageEvent().observe(this,observer);
    }

    /**
     * 注册单个消息事件，消息对象:{@link Message}
     * @param observer
     */
    protected void registerSingleLiveEvent(@NonNull Observer<Message> observer){
        mViewModel.getSingleLiveEvent().observe(this,observer);
    }

    /**
     * 注册状态事件
     * @param observer
     */
    protected void registerStatusEvent(@NonNull StatusEvent.StatusObserver observer){
        mViewModel.getStatusEvent().observe(this,observer);
    }

    public Context getContext(){
        return this;
    }

    /**
     * 是否使用DataBinding
     * @return  默认为true 表示使用。如果为false，则不会初始化{@link #mBinding}。
     */
    @Override
    public boolean isBinding(){
        return true;
    }

    /**
     * 创建ViewModel
     * @return 默认为null，为null时，{@link #mViewModel}会默认根据当前Activity泛型{@link VM }获得ViewModel
     */
    @Override
    public VM createViewModel(){
        return null;
    }

    /**
     * 通过{@link ViewModelProvider.Factory}获得ViewModel
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass){
        return ViewModelProviders.of(this,mViewModelFactory).get(modelClass);
    }

    //---------------------------------------

    protected Intent newIntent(Class<?> cls){
        return new Intent(getContext(),cls);
    }

    protected Intent newIntent(Class<?> cls,int flags){
        Intent intent = newIntent(cls);
        intent.setFlags(flags);
        return intent;
    }

    protected void startActivity(Class<?> cls){
        startActivity(newIntent(cls));
    }

    protected void startActivity(Class<?> cls,int flags){
        startActivity(newIntent(cls,flags));
    }

    protected void startActivityFinish(Class<?> cls){
        startActivity(cls);
        finish();
    }

    protected void startActivityFinish(Class<?> cls,int flags){
        startActivity(cls,flags);
        finish();
    }

    protected void startActivityForResult(Class<?> cls,int requestCode){
        startActivityForResult(newIntent(cls),requestCode);
    }

    //---------------------------------------

    protected View inflate(@LayoutRes int id){
        return inflate(id,null);
    }

    protected View inflate(@LayoutRes int id,@Nullable ViewGroup root){
        return LayoutInflater.from(getContext()).inflate(id,root);
    }

    protected View inflate(@LayoutRes int id,@Nullable ViewGroup root, boolean attachToRoot){
        return LayoutInflater.from(getContext()).inflate(id,root,attachToRoot);
    }

    //---------------------------------------

    protected void showDialogFragment(DialogFragment dialogFragment){
        String tag = dialogFragment.getTag() !=null ? dialogFragment.getTag() : dialogFragment.getClass().getSimpleName();
        showDialogFragment(dialogFragment,tag);
    }

    protected void showDialogFragment(DialogFragment dialogFragment,String tag) {
        dialogFragment.show(getSupportFragmentManager(),tag);
    }

    protected void showDialogFragment(DialogFragment dialogFragment, FragmentManager fragmentManager, String tag) {
        dialogFragment.show(fragmentManager,tag);
    }

    private View.OnClickListener mOnDialogCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismissDialog();

        }
    };

    protected Dialog getDialog(){
        return this.mDialog;
    }

    protected Dialog getProgressDialog(){
        return this.mProgressDialog;
    }

    protected View.OnClickListener getDialogCancelClick(){
        return mOnDialogCancelClick;
    }

    protected void dismissDialog(){
        dismissDialog(mDialog);
    }

    protected void dismissDialog(Dialog dialog){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    protected void dismissPopupWindow(PopupWindow popupWindow){
        if(popupWindow!=null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    protected void dismissProgressDialog(){
        dismissDialog(mProgressDialog);
    }

    protected void showProgressDialog(){
        showProgressDialog(false);
    }

    protected void showProgressDialog(boolean isCancel){
        showProgressDialog(R.layout.progress_dialog,isCancel);
    }

    protected void showProgressDialog(@LayoutRes int resId){
        showProgressDialog(resId,false);
    }

    protected void showProgressDialog(@LayoutRes int resId,boolean isCancel){
        showProgressDialog(inflate(resId),isCancel);
    }

    protected void showProgressDialog(View v){
        showProgressDialog(v,false);
    }

    protected void showProgressDialog(View v,boolean isCancel){
        dismissProgressDialog();
        mProgressDialog =  BaseProgressDialog.newInstance(getContext());
        mProgressDialog.setContentView(v);
        mProgressDialog.setCanceledOnTouchOutside(isCancel);
        mProgressDialog.show();
    }

    protected void showDialog(View contentView){
        showDialog(contentView,DEFAULT_WIDTH_RATIO);
    }

    protected void showDialog(View contentView,boolean isCancel){
        showDialog(getContext(),contentView,R.style.dialog,DEFAULT_WIDTH_RATIO,isCancel);
    }

    protected void showDialog(View contentView,float widthRatio){
        showDialog(getContext(),contentView,widthRatio);
    }

    protected void showDialog(View contentView,float widthRatio,boolean isCancel){
        showDialog(getContext(),contentView,R.style.dialog,widthRatio,isCancel);
    }

    protected void showDialog(Context context,View contentView,float widthRatio){
        showDialog(context,contentView, R.style.dialog,widthRatio);
    }

    protected void showDialog(Context context, View contentView, @StyleRes int resId, float widthRatio){
        showDialog(context,contentView,resId,widthRatio,true);
    }

    protected void showDialog(Context context, View contentView, @StyleRes int resId, float widthRatio,final boolean isCancel){
        dismissDialog();
        mDialog = new Dialog(context,resId);
        mDialog.setContentView(contentView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && isCancel){
                    dismissDialog();
                }
                return true;

            }
        });
        setDialogWindow(mDialog,widthRatio);
        mDialog.show();

    }

    protected void setDialogWindow(Dialog dialog, float widthRatio){
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int)(getWidthPixels() * widthRatio);
        window.setAttributes(lp);
    }

    //---------------------------------------

    protected DisplayMetrics getDisplayMetrics(){
        return getResources().getDisplayMetrics();
    }

    protected int getWidthPixels(){
        return getDisplayMetrics().widthPixels;
    }

    protected int getHeightPixels(){
        return getDisplayMetrics().heightPixels;
    }

}
