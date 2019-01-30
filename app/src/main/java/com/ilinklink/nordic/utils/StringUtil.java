/**
 *
 */
package com.ilinklink.nordic.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 字符串操作工具类
 *
 * @author chuck
 */
public class StringUtil {

    public static final String EMPTY = "";

    public static boolean isEmpty(String text) {
        return null == text || "".equals(text) || " ".equals(text) || "null".equals(text);
    }

    /**
     * @param : [str]
     * @return type: java.lang.String
     *
     * @method name: substring
     * @des: 截取价格类型字符串后面的.00, 例如11.00截取后返回11
     * @date 创建时间：2015/11/23 10:36
     */
    public static String substring(String str) {
        String s = "";
        if (!android.text.TextUtils.isEmpty(str)) {
            if (str.indexOf(".") != -1) {
                s = str.substring(0, str.indexOf("."));
            }
        }
        return s;
    }

    public static String str2HexStr(String str) {
        if(TextUtils.isEmpty(str)){
            return "";
        }
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转换为字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value)
    {
        int len2 = (value.length())/2;
        StringBuffer sbu = new StringBuffer();
        for(int i=1;i<=len2;i++){
            int get1 = Integer.parseInt(value.substring(2*(i-1), 2*i), 16);
            sbu.append((char) get1);
        }
        return sbu.toString();
    }

    public static String intArrayToHexString(int[] array){

        if(array==null||array.length==0){
            return null;
        }
        else{
            String result="";
            for(int i=0;i<array.length;i++){
                //ip+=Integer.toString(keyInt[i],16);
                String each= Integer.toHexString(array[i]).toUpperCase();
                if(each.length()==1){
                    each="0"+each;
                }
                result+=each;
            }
            return result.toUpperCase();
        }
    }

    //获取随机的秘钥
    public static String getAesHexRandom(){

        //48
        String result="20572F52364B3F473050415811632D2B";

        try {
            StringBuilder stringBuilder=new StringBuilder();
            char[] chars = "0123456789ABCDEF".toCharArray();
            Random random=new Random();

            for(int i=0;i<32;i++){//直接生产hex,16字节
                stringBuilder.append(chars[random.nextInt(chars.length)]);
            }

            result=stringBuilder.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            return result;
        }




    }


    //获取随机的秘钥
    public int[] getPwdASCIIARandom(){
        //48
        int [] bukket={48,49,50,51,52,53,54,55,56,57};//字符'0'到字符'9'的ASCII码

        int [] result={48,48,48,48,48,48};//默认的6个字节

        try {
            Random random=new Random();

            for(int i=0;i<result.length;i++){
                result[i]=bukket[random.nextInt(bukket.length)];
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        finally {
            return result;
        }
    }

    /**
     * @method name:parsePortHex
     * @des:端口
     * @param :[portInt]
     * @return type:java.lang.String
     * @date 创建时间:2018/7/12
     * @author Chuck
     **/
    public static String parsePortHex(int portInt) throws Exception {

        if(portInt>=65535){
            throw new Exception("illegal args!");
        }

        try {
            String port= Integer.toHexString(portInt).toUpperCase();

            int lenth=port.length();
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<4-lenth;i++){
                sb.append("0");
            }
            sb.append(port);
            port=sb.toString().toUpperCase();
            return port;
        } catch (Exception e) {
            e.printStackTrace();
            return ("FFFF");
        }
    }

    public static ArrayList<String> subString(String sourse, int eachLength, boolean addZeroAtLast){
        if(TextUtils.isEmpty(sourse)||eachLength==0){
            return null;
        }
        else{
            ArrayList<String> result=new ArrayList<>();
            int length=sourse.length();
            int size=length/eachLength;
            if(size==0){
                size=1;
            }
            else{
                int p=length%eachLength;
                if(p!=0){
                    p=1;
                }
                size+=p;
            }

            for (int i=0;i<size;i++){
                if(i==size-1){
                    String last=sourse.substring(i*eachLength,sourse.length());
                    if(!addZeroAtLast){//尾数无需补零
                        result.add(last);
                    }
                    else{//尾数需要补0
                        if(last.length()==eachLength){//无尾数
                            result.add(last);
                        }
                        else{
                            int count=eachLength-last.length();
                            for (int k=0;k<count;k++){
                                last+="0";//这是字符串,不是字节,所以加一个"0"
                            }
                            result.add(last);
                        }
                    }
                }
                else{
                    result.add(sourse.substring(i*eachLength,i*eachLength+eachLength));
                }
            }
            return result;
        }
    }



}
