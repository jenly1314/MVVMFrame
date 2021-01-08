package com.king.mvvmframe.kotlin

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.mvvmframe.kotlin.app.oil.OilPriceActivity
import com.king.mvvmframe.kotlin.app.oil.OilPriceViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * Hilt大幅简化了Dagger2的用法，使得我们不用通过@Component注解去编写桥接层的逻辑，但是也因此限定了注入功能只能从几个Android固定的入口点开始，
 * Hilt一共支持6个入口点，分别是：
 * Application
 * Activity
 * Fragment
 * View
 * Service
 * BroadcastReceiver
 *
 * //========================================================//
 *
 * 其中，只有Application这个入口点是使用@HiltAndroidApp注解来声明的
 *
 * @example Application
 * //-------------------------
 *    @HiltAndroidApp
 *    public class YourApplication extends Application {
 *
 *    }
 * //-------------------------
 *
 * //--------------------------------------------------------//
 *
 * 其他的所有入口点，都是用@AndroidEntryPoint注解来声明
 *
 * @example Activity
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourActivity extends BaseActivity {
 *
 *    }
 * //-------------------------
 *
 * @example Fragment
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourFragment extends BaseFragment {
 *
 *    }
 * //-------------------------
 *
 * @example Service
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourService extends BaseService {
 *
 *    }
 * //-------------------------
 *
 * @example BroadcastReceiver
 * //-------------------------
 *    @AndroidEntryPoint
 *    public class YourBroadcastReceiver extends BaseBroadcastReceiver {
 *
 *    }
 * //-------------------------
 *
 * //========================================================//
 *
 *
 * Kotlin 示例
 *
 * 主要示例请查看 [OilPriceActivity] 和 [OilPriceViewModel]
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<BaseViewModel<BaseModel>,ViewDataBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.main_activity
    }

    override fun isBinding(): Boolean {
        //不覆写此方法时，默认返回true，这里返回false表示不使用DataBinding，因为当前界面比较简单，完全没有必要用
        return false
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    fun onClick(v: View){
        when(v.id){
            R.id.btn1 -> startActivity(OilPriceActivity::class.java)
        }
    }
}