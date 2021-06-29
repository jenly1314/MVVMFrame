package com.king.frame.mvvmframe.base.livedata;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 提供观察状态事件
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class StatusEvent extends SingleLiveEvent<Integer> {


    public void observe(LifecycleOwner owner,@NonNull final StatusEvent.StatusObserver observer) {
        super.observe(owner, t -> {
            if (t != null) {
                observer.onStatusChanged(t);
            }
        });
    }

    public interface StatusObserver{
        void onStatusChanged(@Status int status);
    }

    /**
     * 状态
     */
    @IntDef({Status.LOADING, Status.SUCCESS, Status.FAILURE, Status.ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
        int LOADING = 0;
        int SUCCESS = 1;
        int FAILURE = 2;
        int ERROR = 3;
    }
}
