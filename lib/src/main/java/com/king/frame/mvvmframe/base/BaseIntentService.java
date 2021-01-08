//package com.king.frame.mvvmframe.base;
//
//import android.app.IntentService;
//
//import dagger.android.AndroidInjection;
//
///**
// * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
// */
//public abstract class BaseIntentService extends IntentService {
//
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
//    public BaseIntentService(String name) {
//        super(name);
//    }
//
//    @Override
//    public void onCreate() {
//        AndroidInjection.inject(this);
//        super.onCreate();
//    }
//}
