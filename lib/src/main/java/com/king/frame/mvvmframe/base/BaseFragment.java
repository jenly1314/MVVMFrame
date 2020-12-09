package com.king.frame.mvvmframe.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.king.frame.mvvmframe.R;
import com.king.frame.mvvmframe.base.livedata.MessageEvent;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class BaseFragment<VM extends BaseViewModel,VDB extends ViewDataBinding> extends Fragment implements IView<VM>, BaseNavigator, HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> mAndroidInjector;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    /**
     * 请通过 {@link #getViewModel()}获取，后续版本 {@link #mViewModel}可能会私有化
     */
    private VM mViewModel;
    /**
     * 请通过 {@link #getViewDataBinding()}获取，后续版本 {@link #mBinding}可能会私有化
     */
    private VDB mBinding;
    /**
     * 请通过 {@link #getRootView()} ()}获取，后续版本 {@link #mRootView}可能会私有化
     */
    private View mRootView;

    protected static final float DEFAULT_WIDTH_RATIO = 0.85f;

    private Dialog mDialog;

    private Dialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        //Dagger.Android Fragment 注入
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = createRootView(inflater,container,savedInstanceState);
        if(isBinding()){
            mBinding = DataBindingUtil.bind(mRootView);
        }
        initViewModel();
        return mRootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 创建 {@link #mRootView}
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected View createRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(getLayoutId(),container,false);
    }

    /**
     * 获取rootView
     * @return {@link #mRootView}
     */
    protected View getRootView(){
        return mRootView;
    }

    /**
     * 初始化 {@link #mViewModel}
     */
    private void initViewModel(){
        mViewModel = createViewModel();
        if (mViewModel == null) {
            mViewModel = obtainViewModel(getVMClass());
        }

        if(mViewModel != null){
            getLifecycle().addObserver(mViewModel);
        }
        registerLoadingEvent();
    }

    private Class<VM> getVMClass(){
        Class cls = getClass();
        Class<VM> vmClass = null;
        while (vmClass == null && cls!= null){
            vmClass = getVMClass(cls);
            cls = cls.getSuperclass();
        }
        if(vmClass == null){
            vmClass = (Class<VM>) BaseViewModel.class;
        }
        return vmClass;
    }

    private Class getVMClass(Class cls){
        Type type = cls.getGenericSuperclass();
        if(type instanceof ParameterizedType){
            Type[] types = ((ParameterizedType)type).getActualTypeArguments();
            for(Type t : types){
                if(t instanceof Class){
                    Class vmClass = (Class)t;
                    if(BaseViewModel.class.isAssignableFrom(vmClass)){
                        return vmClass;
                    }
                }else if(t instanceof ParameterizedType){
                    Type rawType = ((ParameterizedType)t).getRawType();
                    if(rawType instanceof Class){
                        Class vmClass = (Class)rawType;
                        if(BaseViewModel.class.isAssignableFrom(vmClass)){
                            return vmClass;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mAndroidInjector;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mViewModel != null){
            getLifecycle().removeObserver(mViewModel);
            mViewModel = null;
        }

        if(mBinding != null){
            mBinding.unbind();
        }
    }

    /**
     * 注册状态监听
     */
    protected void registerLoadingEvent(){
        mViewModel.getLoadingEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
        mViewModel.getMessageEvent().observe(getViewLifecycleOwner(),observer);
    }

    /**
     * 注册单个消息事件，消息对象:{@link Message}
     * @param observer
     */
    protected void registerSingleLiveEvent(@NonNull Observer<Message> observer){
        mViewModel.getSingleLiveEvent().observe(getViewLifecycleOwner(),observer);
    }

    /**
     * 注册状态事件
     * @param observer
     */
    protected void registerStatusEvent(@NonNull StatusEvent.StatusObserver observer){
        mViewModel.getStatusEvent().observe(getViewLifecycleOwner(),observer);
    }

    /**
     * 是否使用DataBinding
     * @return  默认为true 表示使用。如果为false，则不会初始化 {@link #mBinding}。
     */
    @Override
    public boolean isBinding(){
        return true;
    }

    /**
     * 创建ViewModel
     * @return 默认为null，为null时，{@link #mViewModel}会默认根据当前Activity泛型 {@link VM}获得ViewModel
     */
    @Override
    public VM createViewModel(){
        return null;
    }

    /**
     * 获取 ViewModel
     * @return {@link #mViewModel}
     */
    public VM getViewModel(){
        return mViewModel;
    }

    /**
     * 获取 ViewDataBinding
     * @return {@link #mBinding}
     */
    public VDB getViewDataBinding(){
        return mBinding;
    }

    /**
     * 通过 {@link ViewModelProvider.Factory}获得 ViewModel
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T obtainViewModel(@NonNull Class<T> modelClass){
        return obtainViewModel(getViewModelStore(),modelClass);
    }

    /**
     * 通过 {@link ViewModelProvider.Factory}获得 ViewModel
     * @param store
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T obtainViewModel(@NonNull ViewModelStore store,@NonNull Class<T> modelClass){
        return createViewModelProvider(store).get(modelClass);
    }

    /**
     * @deprecated 请使用 {@link #obtainViewModel(Class)}
     * @param modelClass
     * @param <T>
     * @return
     */
    @Deprecated
    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass){
        return obtainViewModel(modelClass);
    }

    /**
     * @deprecated 请使用 {@link #obtainViewModel(ViewModelStore, Class)}
     * @param store
     * @param modelClass
     * @param <T>
     * @return
     */
    @Deprecated
    public <T extends ViewModel> T getViewModel(@NonNull ViewModelStore store,@NonNull Class<T> modelClass){
        return obtainViewModel(store,modelClass);
    }

    /**
     * 创建 {@link ViewModelProvider}
     * @param store
     * @return
     */
    private ViewModelProvider createViewModelProvider(@NonNull ViewModelStore store){
        return new ViewModelProvider(store,mViewModelFactory);
    }

    protected ViewModelProvider.Factory getViewModelFactory(){
        return mViewModelFactory;
    }

    //---------------------------------------
    protected void finish(){
        getActivity().finish();
    }

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

    protected View inflate(@LayoutRes int id, @Nullable ViewGroup root){
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
        dialogFragment.show(getFragmentManager(),tag);
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
        showProgressDialog(R.layout.mvvmframe_progress_dialog,isCancel);
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
        showDialog(getContext(),contentView,R.style.mvvmframe_dialog,DEFAULT_WIDTH_RATIO,isCancel);
    }

    protected void showDialog(View contentView,float widthRatio){
        showDialog(getContext(),contentView,widthRatio);
    }

    protected void showDialog(View contentView,float widthRatio,boolean isCancel){
        showDialog(getContext(),contentView,R.style.mvvmframe_dialog,widthRatio,isCancel);
    }

    protected void showDialog(Context context,View contentView,float widthRatio){
        showDialog(context,contentView, R.style.mvvmframe_dialog,widthRatio);
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
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    if(isCancel){
                        dismissDialog();
                    }
                    return true;
                }
                return false;

            }
        });
        setDialogWindow(mDialog,widthRatio);
        mDialog.show();

    }

    protected void setDialogWindow(Dialog dialog, float widthRatio){
        setWindow(dialog.getWindow(),widthRatio);
    }

    protected void setWindow(Window window, float widthRatio){
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
