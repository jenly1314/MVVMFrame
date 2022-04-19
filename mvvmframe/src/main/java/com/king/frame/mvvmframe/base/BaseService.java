package com.king.frame.mvvmframe.base;

import android.app.Service;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * <p>如果您继承使用了 BaseService 或其子类，你需要参照如下方式添加 @AndroidEntryPoint 注解
 *
 * <p>Example:
 * <pre>
 *    &#64;AndroidEntryPoint
 *    public class YourService extends BaseService {
 *
 *    }
 * </pre>
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class BaseService extends Service {

}
