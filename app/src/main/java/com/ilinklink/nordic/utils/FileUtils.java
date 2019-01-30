/**********************************************************
 * Copyright © 2013-1014 深圳市美传网络科技有限公司版权所有
 * 创 建 人：yangbo
 * 创 建 日 期：2014-7-18 下午4:30:04
 * 版 本 号：
 * 修 改 人：
 * 描 述：
 * <p>
 *	
 * </p>
 **********************************************************/
package com.ilinklink.nordic.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 * 
 * @author yangbo
 * @date 2014-7-18
 * @version
 * @since
 */
public class FileUtils {
	private static String TAG="FileUtils";
	private static String JPG=".jpg";
	public static final String FileSeparator = "/";



	public static long readSDCard() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			long availCount = sf.getAvailableBlocks();
			Log.v("readSDCard", "block大小:" + blockSize + ",block数目:"
					+ blockCount + ",总大小:" + blockSize * blockCount / 1024
					+ "KB");
			Log.v("readSDCard", "可用的block数目：:" + availCount + ",剩余空间:"
					+ availCount * blockSize / 1024 + "KB");
			return availCount * blockSize / 1024;
		}
		return 0;
	}

	static public boolean ExistSDCard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	static public String createDirInSDCard(String dir) {
		File dirFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + dir + File.separator);
		if (!dirFile.exists()) {
			if (!dirFile.mkdirs()) {
				return null;
			}
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator + dir + File.separator;
	}





	/**
	 * 没有sd卡的状况下，获取存储文件的路径
	 *
	 * @return
	 */
	private static String getSaveFilePath(Context context) {
		String path = context.getFilesDir().getPath();
		String retPath = path + File.separator;
		return retPath;
	}






	/**
	 * 判断SD卡上的文件夹是否存在
	 *
	 * @param fileName
	 * @return boolean
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}











    // 删除所有文件
    public static void deleteall_ext2(File file) {
        if (file.isFile()) {

            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                //file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                if (childFiles[i].getAbsolutePath().contains("cache"))
                    deleteall_ext3(childFiles[i]);
            }
            //file.delete();
        }
    }
    public static void deleteall_ext3(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                //file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                childFiles[i].delete();
            }
            //file.delete();
        }
    }
    // 删除所有文件
    public static void deleteall_ext(File file) {
        if (file.isFile()) {

            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                //file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                if (childFiles[i].getAbsolutePath().contains("webview"))
                deleteall_ext(childFiles[i]);
            }
            file.delete();
        }
    }
	// 删除所有文件
	public static void deleteall(File file) {
		if (file.isFile()) {

			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				deleteall(childFiles[i]);
			}
			file.delete();
		}
	}

	// 删除子文件集合
	public static void deleteSubs(File file) {

		if (file.isFile()) {//不删文件夹本身
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				return;//空文件夹不删
			}

			for (int i = 0; i < childFiles.length; i++) {
				deleteall(childFiles[i]);//遍历删除
			}
		}
	}

	public static long getFileSize(File f) throws Exception {
		long size = 0;
		File[] flist = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static long getlist(File f) {// 递归求取目录文件个数
		long size = 0;
		File[] flist = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getlist(flist[i]);
				size--;
			}
		}
		return size;
	}

	public static List<Uri> getListUriFromFile(String path) {
		List<Uri> uris = new ArrayList<Uri>();
		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// 如果是目录 递归目录
				getListUriFromFile(files[i].getAbsolutePath());
			} else {
				uris.add(Uri.fromFile(files[i]));
			}
		}
		return uris;
	}

	/**
	 * 将dir和name拼接成完整的路径
	 *
	 * @param dir
	 * @param name
	 * @return
	 */
	public static String catFullPath(String dir, String name) {
		if (dir.endsWith(FileSeparator)) {
			return dir + name;
		} else {
			return dir + FileSeparator + name;
		}
	}

	public static String getParentPath(String path) {
		if (TextUtils.isEmpty(path) || path.equals(FileSeparator)) {
			return "";
		}
		if (path.endsWith(FileSeparator)) {
			path = path.substring(0, path.lastIndexOf(FileSeparator));
		}
		return path.substring(0, path.lastIndexOf(FileSeparator));
	}

	public static String getName(String absolutePath) {
		if (TextUtils.isEmpty(absolutePath)
				|| absolutePath.equals(FileSeparator)) {
			return "";
		}
		if (absolutePath.endsWith(FileSeparator)) {
			absolutePath = absolutePath.substring(0,
					absolutePath.lastIndexOf(FileSeparator));
		}
		if (TextUtils.isEmpty(absolutePath)) {
			return "";
		}
		return absolutePath
				.substring(absolutePath.lastIndexOf(FileSeparator) + 1);
	}

	/**
	 * 文件重命名
	 *
	 * @param oldname
	 * @param newname
	 */
	public static void renameFile(String oldname, String newname) {
		renameFile(new File(oldname), new File(newname));
	}

	/**
	 * 文件重命名
	 * @param oldFile
	 * @param newFile
	 */
	public static void renameFile(File oldFile, File newFile) {
		try {
			fileChannelCopy(oldFile, newFile);
			// 删除原文件
			oldFile.delete();
		} catch (Exception e) {
			Log.e("[error]","文件重命名失败!"+ e);
		}
	}
    /**
     * @method name: copyFileByAssert
     * @des: 从assert 中读取文件
     * @param :[sourcePath, t, ctx]
     * @return type:void
     * @date 创建时间：2015/5/23
     * @author zsw
     */
    public static void copyFileByAssert(String sourcePath, File t, Context ctx)
    {
        try {
            InputStream ss = ctx.getAssets().open(sourcePath);
            copyFileByStream(ss, t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @method name: copyFileByStream
     * @des: 从文件流里面读到文件中
     * @param :[s, t]
     * @return type:void
     * @date 创建时间：2015/5/23
     * @author zsw
     */
	public static void copyFileByStream(InputStream s, File t)
    {
        InputStream fis = null;
        OutputStream fos = null;

        try {
            fis = s;
            fos = new BufferedOutputStream(new FileOutputStream(t));
            byte[] buf = new byte[1024];
            int i ;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf,0,i);
            }
        } catch (Exception e) {

        }finally{
            try{
                fis.close();
                fos.close();
            }catch(Exception e){

            }
        }
    }
	/**
	 * 使用缓冲流的方式复制文件
	 * @param s
	 * @param t
	 */
	public static void copyFile(File s, File t){
		InputStream fis = null;
		OutputStream fos = null;

		try {
			fis = new BufferedInputStream(new FileInputStream(s));
			fos = new BufferedOutputStream(new FileOutputStream(t));
			byte[] buf = new byte[1024];
			int i ;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf,0,i);
			}
		} catch (Exception e) {

		}finally{
			try{
				fis.close();
				fos.close();
			}catch(Exception e){

			}
		}


	}

	/**
	 * 使用文件通道的方式复制文件
	 * @param s  源文件
	 * @param t  复制到的新文件
	 */
	public static void fileChannelCopy(File s, File t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			Log.e("[error]", "文件复制发生异常!"+e);
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				Log.e("[error]", "文件复制,资源释放时发生异常"+e);
			}
		}
	}


	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}
    /**
     * @method name: WriteFile
     * @des:
     * @param :[fileName, fileContent, ctx, mask]
     * @return type:void
     * @date 创建时间：2015/5/23
     * @author zsw
     */
    public static void WriteFile(String fileName, String fileContent, Context ctx, int mask) throws Exception
    {
        // Activity的父类的父类就是context，context与其他框架中的context相同为我们以供了一些核心操作工具。
        FileOutputStream fileOutputStream = ctx.openFileOutput(
                fileName, mask);
        fileOutputStream.write(fileContent.getBytes());
    }
    /**
     * @method name: ReadFile
     * @des:
     * @param :[fileName, ctx]
     * @return type:java.lang.String
     * @date 创建时间：2015/5/23
     * @author zsw
     */
    public static String ReadFile(String fileName, Context ctx) throws Exception
    {
        FileInputStream fileInputStream = ctx.openFileInput(fileName);
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(buffer)) > 0) {
            byteArray.write(buffer, 0, len);
        }
		return byteArray.toString();
    }

    /**
     * 删除/data/data目录下的文件
     * @param fileName
     */
    public static void deleteDataDataFile(Context ctx, String fileName)
    {
    	 String filePath = ctx.getFilesDir() + File.separator+fileName;
         File file = new File(filePath);

         if (file.exists()) {
			boolean result=file.delete();
			Log.d("FileUtils", "---->deleteDataDataFile: fileName="+fileName+" delete result="+result);
         }
    }

	public static String createFileNameByUrl(String url){
		if(TextUtils.isEmpty(url)){
			return  null;
		}
		else {
			return  url.hashCode()+JPG;
		}
	}


	/**
	 * @method name:getAssetsCacheFile
	 * @des:把assets里面的文件缓存起来
	 * @param :[context, fileName]
	 * @return type:java.lang.String
	 * @date 创建时间:2016/6/4
	 * @author Chuck
	 **/
	public static String getAssetsCacheFile(Context context, String fileName)   {

		File cacheFile = new File(context.getCacheDir(), fileName);
		try {
			InputStream inputStream = context.getAssets().open(fileName);
			try {
				FileOutputStream outputStream = new FileOutputStream(cacheFile);
				try {
					byte[] buf = new byte[1024];
					int len;
					while ((len = inputStream.read(buf)) > 0) {
						outputStream.write(buf, 0, len);
					}
				} finally {
					outputStream.close();
				}
			} finally {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cacheFile.getAbsolutePath();
	}

	/**
	 * @method name:getSDPath
	 * @des:获取sd卡路径
	 * @param :[]
	 * @return type:java.lang.String
	 * @date 创建时间:2016/6/4
	 * @author Chuck
	 **/
	public static String getSDPath(){

		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
		if(sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();//获取跟目录
		}
		return sdDir.toString();
	}


	/**
	 * 打印日志到本地
	 * @param body
	 */
	public static void outputLog(String body) {

			//String mLogFilePath= FileUtils.getSDPath() +"/Test/" +"bluetooth_log.doc";
			String mLogFilePath= FileUtils.getSDPath() +"/nazhe/BLE";
			File file= new File(mLogFilePath);

			try {
				if (!file.exists()) {
					file.mkdir();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			String fileName="bluetooth_log.doc";
			File text = new File(mLogFilePath , fileName);

			try {
				if (!text.exists()) {
					text.createNewFile();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean result=text.exists();

			try {
				if(!TextUtils.isEmpty(body)) {
					FileUtils.writerLine(text.getAbsolutePath(),true,","+body,"utf-8");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	static String YMDHMSS = "yyyy/MM/dd HH:mm:ss";
	static SimpleDateFormat mFormat=new SimpleDateFormat(YMDHMSS);
	/**
	 * @param :[path, append, value, charsetName]
	 * @return type:void
	 *
	 * @method name:writerLine
	 * @des:逐行写入文件
	 * @date 创建时间:2016/6/4
	 * @author Chuck
	 **/
	public static void writerLine(String path, boolean append, String value, String charsetName) throws IOException {

		if (new File(path).exists()) {

			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			try {
				osw = new OutputStreamWriter(new FileOutputStream(path, append), charsetName);
				bw = new BufferedWriter(osw);

				bw.write(mFormat.format(new Date()) + value);
				bw.newLine();
				bw.flush();
			} catch (UnsupportedEncodingException e) {
				System.out.println("没有指定的字符集");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.println("没有指定的文件");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("IOException");
			} finally {
				bw.close();
				osw.close();
			}
		}

	}


	public  static boolean LOG_SD=false;
	//打印日志到sd卡
	public static void printLogToSDCard(final String message){
		if(!LOG_SD){//默认是不打印日志到本地的,二代锁,工厂模式下才打印,打印出每一条蓝牙读写报文
			return;
		}
		try {
			LOGUtil.e(TAG,"准备打印日志");
			new Thread(){
				@Override
				public void run() {
					FileUtils.outputLog(message);
				}
			}.start();
		} catch (Exception e) {
			LOGUtil.e(TAG,e.getMessage());
			e.printStackTrace();
		}
	}
}
