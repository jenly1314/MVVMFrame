package com.king.frame.mvvmframe.base.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * 提供观察消息事件
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class MessageEvent extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, final MessageObserver observer) {
        super.observe(owner, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String t) {
            //过滤空消息
            if (TextUtils.isEmpty(t)) {
                return;
            }
            observer.onNewMessage(t);
            }
        });
    }

    public interface MessageObserver{
        void onNewMessage(@NonNull String message);
    }
}
