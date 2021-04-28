package com.king.frame.mvvmframe.base;

import android.app.IntentService;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * 如果您继承使用了BaseIntentService或其子类，你需要参照如下方式添加@AndroidEntryPoint注解
 *
 * @example Service
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourIntentService extends BaseIntentService {
 *
 *    }
 * //-------------------------
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class BaseIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BaseIntentService(String name) {
        super(name);
    }

}
