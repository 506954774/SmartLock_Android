package com.ilinklink.nordic.utils;

import android.text.TextUtils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * AESUtil
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2017/3/8  19:37
 * Copyright : 2014-2016 深圳令令科技有限公司-版权所有
 **/
public class AESUtil {

    public static String KEY="E9276D72AF79899CACD1EED704B824D9";

    private int[] key1_hex={0xE9, 0x27, 0x6D, 0x72, 0xAF, 0x79, 0x89, 0x9C, 0xAC, 0xD1, 0xEE, 0xD7, 0x04, 0xB8, 0x24, 0xD9};
    private int[] key2_hex={0x58, 0x7D, 0x8F, 0x53, 0x93, 0x90, 0x8E, 0x8F, 0x9A, 0x03, 0x71, 0x03, 0x0F, 0x4A, 0x4F, 0x9B};
    private int[] key3_hex={0x8D, 0x0C, 0x8D, 0x9E, 0x8F, 0x9A, 0x02, 0x9C, 0x2C, 0x3D, 0x2B, 0x3D, 0x53, 0x94, 0x92, 0x93};
    private int[] key4_hex={0x01, 0x7F, 0x6C, 0x9A, 0x78, 0x90, 0x21, 0x34, 0x7C, 0x8B, 0x9B, 0x92, 0x93, 0x72, 0x93, 0x7A};



    public static String key1="E9276D72AF79899CACD1EED704B824D9";
    public static String key2="587D8F5393908E8F9A0371030F4A4F9B";
    public static String key3="8D0C8D9E8F9A029C2C3D2B3D53949293";
    public static String key4="017F6C9A789021347C8B9B929372937A";
    public static String key5="82040185301306750013D97056152C87";
    //0x82, 0x04, 0x01, 0x85, 0x30, 0x13, 0x06, 0x75, 0x00, 0x13, 0xd9, 0x70, 0x56, 0x15, 0x2c, 0x87,

    public static String[] KEY_ARRAY ={key1,key2,key3,key4,key5};



    private static String KEY_80=key1+key2+key3+key4+key5;

    static {
        //如果要修改秘钥集合,在这里修改:  (2018/07/13)
        String origial="123456789q123456789w123456789u123456789v123456789p123456789o123456789t1234567898";
        KEY_80=StringUtil.str2HexStr(origial);
    }

    public static String getKeyByIndex(int index){
        if(index>=0&&index<=KEY_80.length()-16-1){
            //赋值之后再返回
            return KEY=KEY_80.substring(index*2,index*2+32);
        }
        return null;
    }

    /**
     * @method name:getKeyByIndex
     * @des:keyBucket 秘钥桶,字符串
     * @param :[keyBucket, index]
     * @return type:java.lang.String
     * @date 创建时间:2018/7/13
     * @author Chuck
     **/
    public static String getKeyByIndex(String keyBucket, int index ){
        if(index>=0&&index<=keyBucket.length()-16-1){
            //赋值之后再返回
            KEY_80=StringUtil.str2HexStr(keyBucket);
            return KEY=KEY_80.substring(index*2,index*2+32);
        }
        return null;
    }



    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, byte[] password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password));
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] password) {
        try {
            //NoPadding，PKCS5Padding，ISO10126Padding,
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

            byte[] byteContent = content;

            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @method name:decrypt
     * @des:  动态解密
     * @param :[keyIndex, secretString]
     * @return type:java.lang.String
     * @date 创建时间:2017/3/13
     * @author Chuck
     **/
    public static String decrypt(int keyIndex, String secretString){

        String result=null;
        try {
            String hexKey = KEY_ARRAY[keyIndex];
            byte[] key= ByteUtil.hexStr2Bytes(hexKey);
            byte[] bytesOrignal=AESUtil.decrypt(ByteUtil.hexStr2Bytes(secretString),key);//解密
            result= ByteUtil.byteToHexString(bytesOrignal);//解密得到48个字节的原文
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }

    }



    /**
     * @method name:getTextSecretString
     * @des:  测试获取密文
     * @param :[
     *
     * index:密钥索引
     * timeStamp:时间戳
     * , keySource:16进制,4字节
     * , transactionSerialNumber:交易序列号,18字节的字符串
     * ]
     * @return type:java.lang.String
     * @date 创建时间:2017/3/9
     * @author Chuck
     **/
    private String getTextSecretString80Key(int index, int timeStamp, String keySource, String transactionSerialNumber) {

        String hexKey = AESUtil.getKeyByIndex(index);
        if(!TextUtils.isEmpty(hexKey)){
            byte[] key= ByteUtil.hexStr2Bytes(hexKey);

            StringBuffer sb_aes = new StringBuffer();
            sb_aes.append(Integer.toHexString(timeStamp));//时间戳,秒 4节
            LOGUtil.e("AES","解锁时间戳,16进制string:"+ Integer.toHexString(timeStamp));
            sb_aes.append(keySource);//解锁码,device随机生成,时效性  4节
            sb_aes.append(StringUtil.str2HexStr(transactionSerialNumber));//交易序列号,把字符串转为16进制字符串 18字节
            for (int i = 0; i < 6; i++) {//加密的区域只有26个字节,使用NoPadding的填充模式,必须是16的倍数,所以补6个字节的"00"
                sb_aes.append("00");
            }

            String stringBefore = sb_aes.toString().toUpperCase();
            byte [] bytesBefore=ByteUtil.hexStr2Bytes(stringBefore);

            byte [] bytesAfter=AESUtil.encrypt(bytesBefore,key);//加密
            String stringAfter=ByteUtil.byteToHexString(bytesAfter);


            LOGUtil.e("AES","测试生成的密文,16进制string:"+stringAfter);

            return stringAfter;
        }
       return null;
    }
    /**
     * @method name:getTextSecretString
     * @des:  测试获取密文
     * @param :[
     *
     * index:密钥索引
     * timeStamp:时间戳
     * , keySource:16进制,4字节
     * , transactionSerialNumber:交易序列号,18字节的字符串
     * ]
     * @return type:java.lang.String
     * @date 创建时间:2017/3/9
     * @author Chuck
     **/
    private String getTextSecretString(int index, int timeStamp, String keySource, String transactionSerialNumber) {


        String hexKey = AESUtil.KEY_ARRAY[index];
        byte[] key= ByteUtil.hexStr2Bytes(hexKey);

        StringBuffer sb_aes = new StringBuffer();
        sb_aes.append(Integer.toHexString(timeStamp));//时间戳,秒 4节
        LOGUtil.e("AES","解锁时间戳,16进制string:"+ Integer.toHexString(timeStamp));
        sb_aes.append(keySource);//解锁码,device随机生成,时效性  4节
        sb_aes.append(StringUtil.str2HexStr(transactionSerialNumber));//交易序列号,把字符串转为16进制字符串 18字节
        for (int i = 0; i < 6; i++) {//加密的区域只有26个字节,使用NoPadding的填充模式,必须是16的倍数,所以补6个字节的"00"
            sb_aes.append("00");
        }

        String stringBefore = sb_aes.toString().toUpperCase();
        byte [] bytesBefore=ByteUtil.hexStr2Bytes(stringBefore);

        byte [] bytesAfter=AESUtil.encrypt(bytesBefore,key);//加密
        String stringAfter=ByteUtil.byteToHexString(bytesAfter);


        LOGUtil.e("AES","测试生成的密文,16进制string:"+stringAfter);

        return stringAfter;
    }

    /**
     * @method name:getTextSecretString
     * @des:  测试获取密文
     * @param :[
     *
     * keyBucket:秘钥桶
     * keyIndex:密钥索引
     * keySource:明文
     * ]
     * @return type:java.lang.String
     * @date 创建时间:2017/3/9
     * @author Chuck
     **/
    public static String getTextSecretString80KeyV104(String keyBucket, int keyIndex, String keySource) {

        String hexKey = getKeyByIndex(keyBucket,keyIndex);
        byte[] key= ByteUtil.hexStr2Bytes(hexKey);

        StringBuffer sb_aes = new StringBuffer();
        sb_aes.append(keySource);//解锁码,device随机生成,时效性  4节
        for (int i = 0; i < 4; i++) {//
            sb_aes.append("00");
        }



        String stringBefore = sb_aes.toString().toUpperCase();
        byte [] bytesBefore=stringBefore.getBytes();



        byte [] bytesAfter=encrypt(bytesBefore,key);//加密
        String stringAfter=ByteUtil.byteToHexString(bytesAfter);

        LOGUtil.i("AES","秘钥索引:"+keyIndex);
        LOGUtil.i("AES","秘钥hex:"+ByteUtil.byteToHexString(key));
        LOGUtil.i("AES","明文hex:"+ByteUtil.byteToHexString(bytesBefore));
        LOGUtil.i("AES","密文hex:"+ByteUtil.byteToHexString(bytesAfter));


        LOGUtil.e("AES",stringAfter);

        return stringAfter;
    }




    /**
     * @method name:getTextSecretString
     * @des:  测试获取密文
     * @param :[
     *
     * index:密钥索引
     * timeStamp:时间戳
     * , keySource:16进制,4字节
     * , transactionSerialNumber:交易序列号,18字节的字符串
     * ]
     * @return type:java.lang.String
     * @date 创建时间:2017/3/9
     * @author Chuck
     **/
    public static String getTextSecretString80KeyV104(int keyIndex, String keySource) {

        String hexKey = getKeyByIndex(keyIndex);
        byte[] key= ByteUtil.hexStr2Bytes(hexKey);

        StringBuffer sb_aes = new StringBuffer();
        sb_aes.append(keySource);//解锁码,device随机生成,时效性  4节
        for (int i = 0; i < 4; i++) {//
            sb_aes.append("00");
        }

        String stringBefore = sb_aes.toString().toUpperCase();
        byte [] bytesBefore=stringBefore.getBytes();

        byte [] bytesAfter=encrypt(bytesBefore,key);//加密
        String stringAfter=ByteUtil.byteToHexString(bytesAfter);

        LOGUtil.e("AES",stringAfter);

        return stringAfter;
    }





}
