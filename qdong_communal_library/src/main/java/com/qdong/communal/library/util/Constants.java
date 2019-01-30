/*****************************************************
 * Copyright % 2014-1201 ��������ͨ������Ƽ����޹�˾
 * �����ˣ� zsw
 * �������ڣ�2014-12-3
 * �޸��ˣ�
 * ������
 * <p>
 * <p/>
 * </p>
 *****************************************************/
package com.qdong.communal.library.util;

import android.os.Environment;

import com.qdong.communal.library.BuildConfig;

import java.io.File;


/**
 * <p>
 * 存放全局静态常量，
 * </p>
 *
 * @author chuck
 * @date:2014-12-3
 * @since
 */
public class Constants {

    /*******************************************************
     *文件相关
     *************************************************/

    /**
     * glide文件root目录
     **/
    public static final String GLIDE = "/glide";

    /**
     * 可以在gradle里配置
     **/
    public static final String FILE_ROOT_NAME = BuildConfig.FILE_ROOT_NAME;

    /**
     * 配置获取Glide图片在sd卡的根目录
     */
    public static String getGlideImageRootDir() {
        return Environment.getExternalStorageDirectory() + "/" + FILE_ROOT_NAME + GLIDE;
    }

    /**
     * 获取sd卡项目目录
     */
    public static String getQDongDir() {
        return Environment.getExternalStorageDirectory() + "/" + FILE_ROOT_NAME + "/";
    }

    /**
     * 获取ngqj根目录
     */
    public static String getQDongDirRoot() {
        return Environment.getExternalStorageDirectory() + "/" + FILE_ROOT_NAME;
    }

    /**
     * 获取sd卡图片目录
     */
    public static final String QD_IMAGE_DIR = getQDongDir() + "images";
    /**
     * 获取sd卡personal目录
     */
    public static final String QD_PERSONAL_DIR = getQDongDir() + "saves";

    /**
     * 应用路径
     */
    public static final String STORE_APP_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + FILE_ROOT_NAME + File.separator;

    /**
     * 图片路径
     **/
    public static final String STORE_IMG_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + FILE_ROOT_NAME
            + File.separator
            + "image" + File.separator;


    /**
     * 下载APK路径
     */
    public static final String STORE_APK_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + FILE_ROOT_NAME
            + File.separator
            + "apk" + File.separator;

    public static final String STORE_RECOVERY_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + FILE_ROOT_NAME
            + File.separator
            + "recovery" + File.separator;

    public static final String STORE_BACKUP_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + FILE_ROOT_NAME
            + File.separator
            + "backup" + File.separator;


    /*******************************************************
     * **********************************************************
     * 偏好设置 key
     ********************************************************************/
    public static final String SESSION_ID = "SESSION_ID";
    public static final String HAS_SHOWED_GUID_ACTIVITY = "HAS_SHOWED_GUID_ACTIVITY";//是否展示过引导界面,默认false
    public static final String RECENTLY_CITY = "RECENTLY_CITY";//最近使用的城市名

    /*******************************************************
     * **********************************************************
     * url
     ********************************************************************/
    //http://192.168.0.20:10020/token-service/client/register?appId=YOUR_APPID

    //public static final String DEBUG_HOST="http://192.168.0.20";
    public static final String DEBUG_HOST="http://p2287713g0.51mypc.cn";
    public static final String DEBUG_PORT=":10020/";


//    public static final String PRODUCTION_HOST = "http://47.107.48.132";
    public static final String PRODUCTION_HOST = "http://p2287713g0.51mypc.cn";
    public static final String PRODUCTION_PORT = ":10020/";

    public static final String APP_ID = "songi_android";//appId


    //服务
    public static final String TOKEN_SERVICE = "token-service";
    public static final String SMS_SERVICE = "sms-service";
    public static final String USER_AUTHC_SERVICE = "user-authc-service";
    public static final String SONGI_DEVICE_SERVICE = "songi-device-service";
    public static final String USER_INFO_SERVICE = "user-info-service";
    public static final String PUSH_SERVICE = "push-service";
    public static final String DICT_SERVICE = "dict-service";
    public static final String DEVICE_INFO_SERVICE = "device-info-service";
    public static final String FILE_SERVICE = "file-service";
    public static final String CONSULT_INFO_SERVICE = "consult-info-service";
    public static final String STORE_INFO_SERVICE = "store-info-service";
    public static final String POLICY_INFO_SERVICE = "policy-info-service";
    public static final String PAY_INFO_SERVICE = "pay-info-service";
    public static final String INFORMATION_INFO_SERVICE = "information-info-service";
    public static final String FACTORY_INFO_SERVICE = "factory-info-service";
    public static final String MESSAGE_INFO_SERVICE = "message-info-service";


//    public static final String SERVER_URL = "http://101.201.82.183:12002/";//baseUrl,测试环境
    //public static final String SERVER_URL = "http://101.201.82.183:15001/";//baseUrl,正式环境


    public static final String SERVER_URL_DEBUG = DEBUG_HOST + DEBUG_PORT;//测试环境
    public static final String SERVER_URL_PRODUCTION = PRODUCTION_HOST + PRODUCTION_PORT;//发布环境


    public static String SERVER_URL = SERVER_URL_DEBUG;


    //public static final String SERVER_URL = "http://101.201.82.183:8080/";//baseUrl,公网
    //public static final String SERVER_URL = "http://192.168.1.187:8088/";//baseUrl,刘尚新
    public static final String SERVER_PREFIX = "AppServer";
    //public static final String SERVER_PREFIX = "app-server";
    public static final String TFS_READ_URL = "http://123.56.202.154:7500/v1/tfs/";//文件读取地址,默认的

    //public static final String SERVER_URL = "http://192.168.1.230:10005/";//baseUrl,默认的
    //Host: 192.168.1.230:10005


    /*********************************************************
     * 错误码,登录相关的
     */
    public static final String USER_NAME_OR_PWD_ERROR = "010008";
    public static final String USER_NOT_EXSIT = "020003";
}
