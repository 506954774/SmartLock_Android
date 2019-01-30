package com.ilinklink.nordic.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtil {

    public static SharedPreferencesUtil INSTANCE;
    private static SharedPreferences mPrefer;	//SharedPreferences对象
    private static final String APP_NAME= "songji";	//保存数据的文件名


    public static SharedPreferencesUtil getInstance(Context context){
        if(INSTANCE==null){
            return new SharedPreferencesUtil(context);
        }
        return INSTANCE;
    }

    private SharedPreferencesUtil(Context context) {
        init(context);
    }

    /**初始化SharedPreferences*/
    public void init(Context context){
        //实例化SharedPreferences
        mPrefer = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 返回的是true，表示传入的字符串是否包含知道的char值序列，
     * @param key
     * @return
     */
    public boolean contains(String key){
        return mPrefer.contains(key);
    }





    /**
     * 保存字符串类型的数据
     * @param key
     * @param value
     */
    public void putString(String key, String value){
        SharedPreferences.Editor edit = mPrefer.edit();	//实例化SharedPreferences.Editor对象
        edit.putString(key, value==null?"":value);	//保存键值
        edit.commit();	//提交数据
    }

    /**
     * 取得字符类型的数据
     * @param key
     * @param value
     * @return
     */
    public String getString(String key, String value){
        return mPrefer.getString(key, value);
    }




    /**
     * 保存整型类型的数据
     * @param key
     * @param value
     */
    public void putInt(String key, int value){
        SharedPreferences.Editor edit = mPrefer.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 获取整型类型的数据
     * @param key
     * @param value
     * @return
     */
    public int getInt(String key, int value){
        return mPrefer.getInt(key, value);
    }

    /**
     * 保存布尔类型的数据
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        mPrefer.edit().putBoolean(key, value).commit();
    }

    /**
     * @param key
     * @param /value
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mPrefer.getBoolean(key, defValue);
    }

    /**
     * 保存long型的数据
     * @param key
     * @param value
     */
    public void putLong(String key, long value){
        mPrefer.edit().putLong(key, value).commit();
    }

    /**
     * 取得long型的数据
     * @param key
     * @param value
     * @return
     */
    public long getLong(String key, long value){
        return mPrefer.getLong(key, value);
    }

    public void removeKey(String key) {
        mPrefer.edit().remove(key).commit();
    }



}
