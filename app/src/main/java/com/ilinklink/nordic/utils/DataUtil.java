package com.ilinklink.nordic.utils;

import android.util.Log;

/**
 * DataUtil
 * Created By:Chuck
 * Des:
 * on 2019/1/9 10:20
 */
public class DataUtil {

    public static final String TAG="DataUtil";


    /**
     * @method name:parseData
     * @des:校验,解析数据
     * @param :[s]
     * @return type:java.lang.String
     * @date 创建时间:2019/1/9
     * @author Chuck
     **/
    public static String parseData(String s){

        try {
            String dataString=s.substring(0,s.length()-4);
            Log.d(TAG, "dataString,HEX: "+dataString);

            byte[] data= ByteUtil.hexStr2Bytes(dataString);

            String check=ByteUtil.toHexString(CRCUtil.crc16Cal(data,dataString.length()/2));

            Log.d(TAG,"check,HEX: "+check);


            if(s.substring(s.length()-4,s.length()).equals(check)){
                Log.d(TAG,"数据校验ok");
                int size=Integer.parseInt(dataString.substring(8,12),16);
                Log.d(TAG,"size: "+size);
                String message=dataString.substring(12,12+size*2);
                Log.d(TAG,"实际数据: "+message);
                return message;
            }
            else{
                Log.d(TAG,"数据校验失败");
                return null;

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.d(TAG,"数据校验失败:"+e.getMessage());
            return null;
        }
    }
}
