package com.ilinklink.nordic.utils;

import android.util.Log;


/**
 * LogUtil
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2016/7/7  15:28
 * Copyright : 趣动智能科技有限公司-版权所有
 **/
public class LOGUtil {

    public  static   boolean LOG= true;//gradle里配置

    public static void i(String tag, Object object){
        if(LOG){
            if(object!=null){
                Log.i(tag,object.toString());
            }
        }
    }
    public static void e(String tag, Object object){
        if(LOG){
            if(object!=null){
                Log.e(tag,object.toString());
            }
        }
    }

    public static void d(String tag, Object object){
        if(LOG){
            if(object!=null){
                Log.d(tag,object.toString());
            }
        }
    }
    public static void w(String tag, Object object){
        if(LOG){
            if(object!=null){
                Log.w(tag,object.toString());
            }
        }
    }



}
