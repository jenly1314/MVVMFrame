package com.king.frame.mvvmframe.util

import android.content.Intent
import android.os.SystemClock
import com.king.logx.LogX

/**
 * 跳转防抖
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
internal class JumpDebounce {
    private var lastTag: String? = null
    private var lastJumpTime: Long = 0L

    /**
     * 是否忽略跳转
     */
    fun isIgnoreJump(intent: Intent, intervalTime: Int = DEBOUNCE_INTERVAL_TIME): Boolean {
        val jumpTag = if (intent.component != null) {
            intent.component!!.className
        } else if (intent.action != null) {
            intent.action
        } else {
            null
        }
        if (jumpTag.isNullOrEmpty()) {
            return false
        }
        if (jumpTag == lastTag && lastJumpTime > SystemClock.elapsedRealtime() - intervalTime) {
            LogX.d("Ignore Intent: $jumpTag")
            return true
        }
        lastTag = jumpTag
        lastJumpTime = SystemClock.elapsedRealtime()
        return false
    }

    companion object {
        const val DEBOUNCE_INTERVAL_TIME = 500
    }
}
