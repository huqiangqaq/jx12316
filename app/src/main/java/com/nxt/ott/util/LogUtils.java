package com.nxt.ott.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class LogUtils {
    private static boolean IS_DEBUG=true;//log开关
    public static void e(String tag,String log){
        if(IS_DEBUG){
            Log.e(tag,log);
        }
    }
    public static void d(String tag,String log){
        if(IS_DEBUG){
            Log.d(tag,log);
        }
    }
    public static void i(String tag,String log){
        if(IS_DEBUG){
            Log.i(tag,log);
        }
    }
}
