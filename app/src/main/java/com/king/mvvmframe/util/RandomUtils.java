package com.king.mvvmframe.util;

import android.graphics.Color;

import java.util.Random;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public enum  RandomUtils {
    INSTANCE;

    private Random mRandom = new Random();

    @ColorInt
    public int randomColor(@IntRange(from=0,to=255) int min, @IntRange(from=0,to=255) int max){
        return Color.rgb(random(min,max),random(min,max),random(min,max));
    }

    public int random(int min,int max){
        return mRandom.nextInt(max - min + 1) + min;
    }
}