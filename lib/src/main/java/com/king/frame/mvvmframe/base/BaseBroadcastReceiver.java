package com.king.frame.mvvmframe.base;

import android.content.BroadcastReceiver;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * 如果您继承使用了BaseBroadcastReceiver或其子类，你需要参照如下方式添加@AndroidEntryPoint注解
 *
 * @example BroadcastReceiver
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourBroadcastReceiver extends BaseBroadcastReceiver {
 *
 *    }
 * //-------------------------
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {

}
