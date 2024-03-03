package com.king.mvvmframe.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import java.util.Random

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
object RandomUtil {

    private val random by lazy {
        Random()
    }

    @ColorInt
    fun randomColor(
        @IntRange(from = 0, to = 255) min: Int,
        @IntRange(from = 0, to = 255) max: Int
    ): Int {
        return Color.rgb(random(min, max), random(min, max), random(min, max))
    }

    fun random(min: Int, max: Int): Int {
        return random.nextInt(max - min + 1) + min
    }
}