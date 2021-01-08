package com.king.frame.mvvmframe.base;

import android.app.Application;
import android.os.Message;

import com.king.frame.mvvmframe.base.livedata.MessageEvent;
import com.king.frame.mvvmframe.base.livedata.SingleLiveEvent;
import com.king.frame.mvvmframe.base.livedata.StatusEvent;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;


/**
 * 标准MVVM模式中的VM (ViewModel)层基类
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IViewModel,ILoading{

    /**
     * 请通过 {@link #getModel()} 获取，后续版本 {@link #mModel}可能会私有化
     */
    private M mModel;

    /**
     * 消息事件
     */
    private MessageEvent mMessageEvent = new MessageEvent();
    /**
     * 状态事件
     */
    private StatusEvent mStatusEvent = new StatusEvent();

    /**
     * 加载状态
     */
    private SingleLiveEvent<Boolean> mLoadingEvent = new SingleLiveEvent<>();

    /**
     * 提供自定义单一消息事件
     */
    private SingleLiveEvent<Message> mSingleLiveEvent  = new SingleLiveEvent<>();

    /**
     * 继承者都将使用此构造
     * @param application
     * @param model
     */
    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.mModel = model;
    }

    /**
     * 特殊构造，仅供内部使用
     * 为了满足@ViewModelInject注解
     * @param application
     */
    @ViewModelInject
    BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if(mModel != null){
            mModel.onDestroy();
            mModel = null;
        }
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    /**
     * {@link M}
     * @return {@link #mModel}
     */
    public M getModel(){
        return this.mModel;
    }

    /**
     *  暴露给观察者提供加载事件，{@link BaseActivity} 或 {@link BaseFragment} 已默认注册加载事件，
     *  只需调用{@link #showLoading()} 或 {@link #hideLoading()}即可在{@link BaseActivity}
     *  或 {@link BaseFragment} 中收到订阅事件
     * @return {@link #mLoadingEvent}
     */
    public SingleLiveEvent<Boolean> getLoadingEvent(){
        return mLoadingEvent;
    }

    /**
     * 暴露给观察者提供消息事件，通过注册{@link BaseActivity#registerMessageEvent(MessageEvent.MessageObserver)}或
     * {@link BaseFragment#registerMessageEvent(MessageEvent.MessageObserver)} 或
     * {@link BaseDialogFragment#registerMessageEvent(MessageEvent.MessageObserver)}接收消息事件
     * @return {@link #mMessageEvent}
     */
    public MessageEvent getMessageEvent(){
        return mMessageEvent;
    }

    /**
     * 暴露给观察者提供状态变化事件，通过注册{@link BaseActivity#registerStatusEvent(StatusEvent.StatusObserver)}或
     * {@link BaseFragment#registerStatusEvent(StatusEvent.StatusObserver)} 或
     * {@link BaseDialogFragment#registerStatusEvent(StatusEvent.StatusObserver)}接收消息事件
     * @return {@link #mStatusEvent}
     */
    public StatusEvent getStatusEvent(){
        return mStatusEvent;
    }

    /**
     * 暴露给观察者提供接收单个消息事件，通过注册{@link BaseActivity#registerSingleLiveEvent(Observer)}或
     * {@link BaseFragment#registerSingleLiveEvent(Observer)} 或
     * {@link BaseDialogFragment#registerSingleLiveEvent(Observer)}接收消息事件
     * @return {@link #mSingleLiveEvent}
     */
    public SingleLiveEvent<Message> getSingleLiveEvent(){
        return mSingleLiveEvent;
    }

    /**
     * 同步发送消息，通过注册{@link BaseActivity#registerMessageEvent(MessageEvent.MessageObserver)}或
     * {@link BaseFragment#registerMessageEvent(MessageEvent.MessageObserver)} 或
     * {@link BaseDialogFragment#registerMessageEvent(MessageEvent.MessageObserver)}接收消息事件，
     * 也可通过观察{@link #getMessageEvent()}接收消息事件
     * @param message 消息内容
     */
    public void sendMessage(String message){
        mMessageEvent.setValue(message);
    }

    /**
     * 同步发送消息，通过注册{@link BaseActivity#registerMessageEvent(MessageEvent.MessageObserver)}或
     * {@link BaseFragment#registerMessageEvent(MessageEvent.MessageObserver)} 或
     * {@link BaseDialogFragment#registerMessageEvent(MessageEvent.MessageObserver)}接收消息事件，
     * 也可通过观察{@link #getMessageEvent()}接收消息事件
     * @param msgId 资源文件id
     */
    public void sendMessage(@StringRes int msgId) {
        sendMessage(getApplication().getString(msgId));
    }

    /**
     * 异步发送消息，通过注册{@link BaseActivity#registerMessageEvent(MessageEvent.MessageObserver)}或
     * {@link BaseFragment#registerMessageEvent(MessageEvent.MessageObserver)} 或
     * {@link BaseDialogFragment#registerMessageEvent(MessageEvent.MessageObserver)}接收消息事件，
     * 也可通过观察{@link #getMessageEvent()}接收消息事件
     * @param message 消息内容
     */
    public void postMessage(String message){
        mMessageEvent.postValue(message);
    }

    /**
     * 同步异步发送消息，通过注册{@link BaseActivity#registerMessageEvent(MessageEvent.MessageObserver)}或
     * {@link BaseFragment#registerMessageEvent(MessageEvent.MessageObserver)} 或
     * {@link BaseDialogFragment#registerMessageEvent(MessageEvent.MessageObserver)}接收消息事件，
     * 也可通过观察{@link #getMessageEvent()}接收消息事件
     * @param msgId 资源文件id
     */
    public void postMessage(@StringRes int msgId) {
        postMessage(getApplication().getString(msgId));
    }

    /**
     * 同步更新状态，通过注册{@link BaseActivity#registerStatusEvent(StatusEvent.StatusObserver)}或
     * {@link BaseFragment#registerStatusEvent(StatusEvent.StatusObserver)} 或
     * {@link BaseDialogFragment#registerStatusEvent(StatusEvent.StatusObserver)}接收消息事件，
     * 也可通过观察{@link #getStatusEvent()}接收消息事件
     * @param status
     */
    public void updateStatus(@StatusEvent.Status int status){
        mStatusEvent.setValue(status);
    }

    /**
     * 异步更新状态，通过注册{@link BaseActivity#registerStatusEvent(StatusEvent.StatusObserver)}或
     * {@link BaseFragment#registerStatusEvent(StatusEvent.StatusObserver)} 或
     * {@link BaseDialogFragment#registerStatusEvent(StatusEvent.StatusObserver)}接收消息事件，
     * 也可通过观察{@link #getStatusEvent()}接收消息事件
     * @param status
     */
    public void postUpdateStatus(@StatusEvent.Status int status){
        mStatusEvent.postValue(status);
    }

    /**
     * 同步发送单个消息事件，消息为{@link Message}对象，可通过{@link Message#what}区分消息类型，用法与{@link Message}一致，
     * 通过注册{@link BaseActivity#registerSingleLiveEvent(Observer)}或
     * {@link BaseFragment#registerSingleLiveEvent(Observer)} 或
     * {@link BaseDialogFragment#registerSingleLiveEvent(Observer)}接收消息事件，
     * 也可通过观察{@link #getSingleLiveEvent()}接收消息事件
     * @param message
     */
    public void sendSingleLiveEvent(Message message){
        mSingleLiveEvent.setValue(message);
    }

    /**
     * 同步发送单个消息事件，消息为{@link Message}对象，可通过{@link Message#what}区分消息类型，用法与{@link Message}一致，
     * 通过注册{@link BaseActivity#registerSingleLiveEvent(Observer)}或
     * {@link BaseFragment#registerSingleLiveEvent(Observer)} 或
     * {@link BaseDialogFragment#registerSingleLiveEvent(Observer)}接收消息事件，
     * 也可通过观察{@link #getSingleLiveEvent()}接收消息事件
     * @param what
     */
    public void sendSingleLiveEvent(int what){
        Message message = Message.obtain();
        message.what = what;
        mSingleLiveEvent.setValue(message);
    }

    /**
     * 异步发送单个消息事件，消息为{@link Message}对象，可通过{@link Message#what}区分消息类型，用法与{@link Message}一致，
     * 通过注册{@link BaseActivity#registerSingleLiveEvent(Observer)}或
     * {@link BaseFragment#registerSingleLiveEvent(Observer)} 或
     * {@link BaseDialogFragment#registerSingleLiveEvent(Observer)}接收消息事件，
     * 也可通过观察{@link #getSingleLiveEvent()}接收消息事件
     * @param message
     */
    public void postSingleLiveEvent(Message message){
        mSingleLiveEvent.postValue(message);
    }

    /**
     * 异步发送单个消息事件，消息为{@link Message}对象，可通过{@link Message#what}区分消息类型，用法与{@link Message}一致，
     * 通过注册{@link BaseActivity#registerSingleLiveEvent(Observer)}或
     * {@link BaseFragment#registerSingleLiveEvent(Observer)} 或
     * {@link BaseDialogFragment#registerSingleLiveEvent(Observer)}接收消息事件，
     * 也可通过观察{@link #getSingleLiveEvent()}接收消息事件
     * @param what
     */
    public void postSingleLiveEvent(int what){
        Message message = Message.obtain();
        message.what = what;
        mSingleLiveEvent.postValue(message);
    }

    /**
     * 调用此类会同步通知执行{@link BaseActivity#showLoading()}或{@link BaseFragment#showLoading()}或
     * {@link BaseDialogFragment#showLoading()}
     */
    @Override
    public void showLoading() {
        mLoadingEvent.setValue(true);
    }

    /**
     * 调用此类会同步通知执行{@link BaseActivity#hideLoading()}或{@link BaseFragment#hideLoading()}或
     * {@link BaseDialogFragment#hideLoading()}
     */
    @Override
    public void hideLoading() {
        mLoadingEvent.setValue(false);
    }

    /**
     * 调用此类会异步通知执行{@link BaseActivity#showLoading()}或{@link BaseFragment#showLoading()}或
     * {@link BaseDialogFragment#showLoading()}
     */
    public void postShowLoading() {
        mLoadingEvent.postValue(true);
    }

    /**
     * 调用此类会异步通知执行{@link BaseActivity#hideLoading()}或{@link BaseFragment#hideLoading()}或
     * {@link BaseDialogFragment#hideLoading()}
     */
    public void postHideLoading() {
        mLoadingEvent.postValue(false);
    }
}
