package com.ilinklink.nordic.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;

import java.util.Arrays;
import java.util.Random;

@SuppressLint("UseValueOf")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ByteUtil {

    private static String HEX = "0123456789ABCDEF";

    /**
     * ���ֽ�����ת��Ϊ16�����ַ���
     *
     * @param data ��Ҫת�����Լ�����
     */
    public static String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            // ȡ������ֽڵĸ�4λ��Ȼ����0x0f�����㣬�õ�һ��0-15֮������ݣ�ͨ��HEX.charAt(0-15)��Ϊ16������
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // ȡ������ֽڵĵ�λ����0x0f�����㣬�õ�һ��0-15֮������ݣ�ͨ��HEX.charAt(0-15)��Ϊ16������
            sb.append(HEX.charAt(b & 0x0f));
        }
        return sb.toString();
    }

    /**
     * ��16�����ַ���ת��Ϊbyte[]�ֽ�����
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStr2Bytes(String hexStr) {
        hexStr=hexStr.toUpperCase();
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = HEX.indexOf(hexs[2 * i]) * 16;
            n += HEX.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return bytes;
    }

    /**
     * �ϲ�����byte����
     *
     * @param byte_1 ����one
     * @param byte_2 ����two
     * @return �ϲ����������
     */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    /**
     * ��һ��byte������ȡ��ָ�����������µ�byte����
     *
     * @param byte_1 Դ����
     * @param start  ��ʼλ��
     * @param end    ����λ��
     * @return copy�����ɵ�������
     */
    public static byte[] byteMerger(byte[] byte_1, int start, int end) {
        byte[] byte_2 = new byte[end];
        byte_2 = Arrays.copyOfRange(byte_1, start, end);
        return byte_2;
    }

    /**
     * ת��shortΪbyte
     *
     * @param b
     * @param s     ��Ҫת����short
     * @param index
     */
    public static void putShort(byte b[], short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index + 0] = (byte) (s >> 0);
    }

    /**
     * ͨ��byte����ȡ��short
     *
     * @param b
     * @param index �ڼ�λ��ʼȡ
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }

    /******************************** javaͨ�õ�int **********************************/
    // /**
    // * ת��intΪbyte����
    // *
    // * @param bb
    // * @param x
    // * @param index
    // */
    // public static void putInt(byte[] bb, int x, int index) {
    // bb[index + 0] = (byte) ((x >> 24) & 0xFF);
    // bb[index + 1] = (byte) ((x >> 16) & 0xFF);
    // bb[index + 2] = (byte) ((x >> 8) & 0xFF);
    // bb[index + 3] = (byte) ((x >> 0) & 0xFF);
    // }
    //
    // /**
    // * ͨ��byte����ȡ��int
    // *
    // * @param bb
    // * @param index
    // * �ڼ�λ��ʼ
    // * @return
    // */
    // public static int getInt(byte[] bb, int index) {
    // return (int) ((((bb[index + 0] & 0xff) << 24)
    // | ((bb[index + 1] & 0xff) << 16)
    // | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
    // }

    /******************************** Cͨ�õ�int **********************************/
    /**
     * ת��intΪbyte����
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putInt(byte[] bb, int x, int index) {
        bb[index + 0] = (byte) ((x >> 0) & 0xFF);
        bb[index + 1] = (byte) ((x >> 8) & 0xFF);
        bb[index + 2] = (byte) ((x >> 16) & 0xFF);
        bb[index + 3] = (byte) ((x >> 24) & 0xFF);
    }

    /**
     * ͨ��byte����ȡ��int
     *
     * @param bb
     * @param index �ڼ�λ��ʼ
     * @return
     */
    public static int getInt(byte[] bb, int index) {
        return (((bb[index + 0] & 0xff) << 0) | ((bb[index + 1] & 0xff) << 8) | ((bb[index + 2] & 0xff) << 16)
                | ((bb[index + 3] & 0xff) << 24));
    }

    /**
     * ת��long��Ϊbyte����
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putLong(byte[] bb, long x, int index) {
        bb[index + 7] = (byte) (x >> 56);
        bb[index + 6] = (byte) (x >> 48);
        bb[index + 5] = (byte) (x >> 40);
        bb[index + 4] = (byte) (x >> 32);
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }

    /**
     * ͨ��byte����ȡ��long
     *
     * @param bb
     * @param index
     * @return
     */
    public static long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 7] & 0xff) << 56) | (((long) bb[index + 6] & 0xff) << 48)
                | (((long) bb[index + 5] & 0xff) << 40) | (((long) bb[index + 4] & 0xff) << 32)
                | (((long) bb[index + 3] & 0xff) << 24) | (((long) bb[index + 2] & 0xff) << 16)
                | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
    }

    /**
     * �ַ����ֽ�ת��
     *
     * @param ch
     * @return
     */
    public static void putChar(byte[] bb, char ch, int index) {
        int temp = (int) ch;
        // byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            bb[index + i] = new Integer(temp & 0xff).byteValue();
            temp = temp >> 8; // ������8λ
        }
    }

    /**
     * �ֽڵ��ַ�ת��
     *
     * @param b
     * @return
     */
    public static char getChar(byte[] b, int index) {
        int s = 0;
        if (b[index + 1] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        s *= 256;
        if (b[index + 0] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        char ch = (char) s;
        return ch;
    }

    /**
     * floatת��byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putFloat(byte[] bb, float x, int index) {
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer(l).byteValue();
            l = l >> 8;
        }
    }

    public static float getFloat(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * doubleת��byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putDouble(byte[] bb, double x, int index) {
        // byte[] b = new byte[8];
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Long(l).byteValue();
            l = l >> 8;
        }
    }

    public static double getDouble(byte[] b, int index) {
        long l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffl;
        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[6] << 48);
        l &= 0xffffffffffffffl;
        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }



    /**
     * @method name:getSignedInt
     * @des:蓝牙信号强度,有正负号,一个字节
     * @param :[hexOneByte]
     * @return type:int
     * @date 创建时间:2017/3/29
     * @author Chuck
     **/
    public static int getSignedInt(String hexOneByte){


        int result=0;
        try {
            int a= Integer.parseInt(hexOneByte,16);//转换为int,不可以转为byte,因为byte是有符号的

            if(a>128){//大于128说明是负数
                a=~a;//先取反码
                String not= Integer.toBinaryString(a).substring(24);//只截取后面的8个bit

                result= Integer.valueOf(not,2);//转为int
                result+=1;//加1
                result*=-1;//加上符号
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            return result;
        }
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

    public static short byteToShort2(byte[] b) {
        return (short) (b[1] & 0xff
                | (b[0] & 0xff) << 8);
    }

    private static final String FORMAT = "%04X";

    public static String toHexString(int num) {
        return String.format(FORMAT, num);
    }
}
