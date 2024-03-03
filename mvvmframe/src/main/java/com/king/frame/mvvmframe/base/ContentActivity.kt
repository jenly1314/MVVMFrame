package com.king.frame.mvvmframe.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.king.frame.mvvmframe.R

/**
 * 用于容纳 [Fragment] 的通用 [Activity]，相当于一个 [Fragment] 容器，通过 [Intent] 传递参数和标识，
 * 然后实现 [switchFragment] 函数来处理对应的逻辑
 *
 * MVVMFrame 框架基于 Google 官方的 JetPack 构建，在使用 MVVMFrame 时，需遵循一些规范：
 *
 * 如果您继承使用了 BaseActivity 或其子类，你需要参照如下方式添加 @AndroidEntryPoint 注解标记，来进行注入
 *
 *
 * ```
 * // 示例
 * @AndroidEntryPoint
 * class YourActivity: ContentActivity() {
 *
 * }
 * ```
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Suppress("unused")
abstract class ContentActivity : BaseActivity<BaseViewModel, ViewDataBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.mvvmframe_content_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        switchFragment(intent)
    }

    override fun isBinding(): Boolean {
        return false
    }

    /**
     * 通过[getSupportFragmentManager]将布局替换成[Fragment]，如在[switchFragment]方法中使用
     */
    open fun replaceFragment(fragment: Fragment) {
        replaceFragment(R.id.fragmentContent, fragment)
    }

    /**
     * 通过[getSupportFragmentManager]将布局替换成[Fragment]
     */
    open fun replaceFragment(@IdRes id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, fragment).commit()
    }

    /**
     * 切换Fragment
     * @param intent 通过[Intent] 传递参数和标记来判断对应展示某个[Fragment]
     */
    abstract fun switchFragment(intent: Intent)
}