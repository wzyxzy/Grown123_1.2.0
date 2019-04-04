package com.pndoo.grown123_new.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileUtil {

    public static long delsize = 0;

    /**
     * 递归的删除目录
     */
    public static boolean deleteDirectory(File file) {
        if (file == null)
            return false;

        if (file.isFile()) {
            return file.delete();
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    delsize += files[i].length();
                    files[i].delete();
                } else if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
            }
        }
        return file.delete();
    }

    public static String SavePicture(String strUrl) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            String strName = StringUtil.MD5Encode(strUrl) + ".jpg";
            String strDescPath = null;
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File sdFile = ActivityUtils.getSDPath();
                strDescPath = sdFile.getAbsolutePath() + "/images/";
            } else {
                // strDescPath = PicPath;
                return null;
            }
            File cacheFile = new File(strDescPath);
            if (!cacheFile.exists()) {
                if (!cacheFile.mkdir())
                    return null;
            }
            Bitmap bmp = ImageUtil.getLargePic().createSafeImage(strUrl);
            File myCaptureFile = new File(strDescPath + strName);
            fos = new FileOutputStream(myCaptureFile);
            bos = new BufferedOutputStream(fos);
            // 采用压缩转档方法
            boolean ret = bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            // 调用flush()方法，更新BufferStream
            bos.flush();
            if (ret) {
                return myCaptureFile.getAbsolutePath();
            } else {
                return null;
            }
            // return file.renameTo(new File(strDescPath + strName));
        } catch (Exception ex) {
            Log.e("FileUtil", "SavePicture", ex);
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (bos != null)
                    bos.close();
            } catch (Exception ex) {
                Log.e("FileUtil", "SavePicture finally", ex);
            }
        }
        return null;
    }

    public static String getCacheData(String strCacheDir, String uid,
                                      String fileName) {
        if (TextUtils.isEmpty(strCacheDir) || TextUtils.isEmpty(uid)
                || TextUtils.isEmpty(fileName)) {
            return null;
        }
        String strContent = null;
        FileInputStream ins = null;
        InputStreamReader inReader = null;
        try {
            String strDir = strCacheDir + "/data/" + uid + "/" + fileName;
            File newsFile = new File(strDir);
            if (!newsFile.exists())
                return null;
            StringBuffer sBuf = new StringBuffer();
            ins = new FileInputStream(newsFile);
            inReader = new InputStreamReader(ins);
            char[] buf = new char[256];
            int len = -1;
            while ((len = inReader.read(buf, 0, 256)) != -1) {
                sBuf.append(buf, 0, len);
            }
            strContent = sBuf.toString();
        } catch (Exception ex) {
            Log.e("FileUtil", "getCacheData", ex);
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (inReader != null) {
                    inReader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return strContent;
    }

    // 路径处理
    private static boolean checkDataDirs(String strCacheDir, String uid) {
        String strDir = strCacheDir + "/data";
        File dataCache = new File(strDir);
        if (!dataCache.exists()) {
            if (!dataCache.mkdirs()) {
                Log.i("checkDataDirs", "!dataCache.mkdir()");
                return false;
            }
        }
        File[] files = dataCache.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                // TODO Auto-generated method stub
                if (pathname.isDirectory()) {
                    return true;
                }
                return false;
            }
        });
        int len = files.length;
        if (len == 10) {
            delOldestFile(files);
        }
        strDir += "/" + uid;
        File userCache = new File(strDir);
        if (!userCache.exists()) {
            if (!userCache.mkdirs()) {
                Log.i("checkDataDirs", "!userCache.mkdir()");
                return false;
            }
        }
        return true;
    }

    /**
     * 找到最老的文件并删除，如果是目录，则递归删掉所有子文件夹及文件
     */
    public static void delOldestFile(File[] files) {
        int len = files.length;
        long curModified = 0;
        int cur = 0;
        for (int i = 0; i < len; i++) {
            File file = files[i];
            long modified = file.lastModified();
            if (i == 0) {
                curModified = modified;
            }
            if (curModified > modified) {
                curModified = modified;
                cur = i;
            }
        }
        File file = files[cur];
        if (!deleteDirectory(file)) {
            Log.i("delOldestFile", "deleteDirectory failed");
        }
    }

    public static boolean setCacheData(String strCacheDir, String uid,
                                       String fileName, String strContent) {
        if (TextUtils.isEmpty(strCacheDir) || TextUtils.isEmpty(uid)
                || TextUtils.isEmpty(fileName) || TextUtils.isEmpty(strContent)) {
            return false;
        }
        FileOutputStream os = null;
        OutputStreamWriter outWriter = null;
        try {
            if (!checkDataDirs(strCacheDir, uid)) {
                return false;
            }
            String strDir = strCacheDir + "/data/" + uid + "/" + fileName;
            File newsFile = new File(strDir);
            if (newsFile.exists()) {
                newsFile.delete();
            }
            os = new FileOutputStream(newsFile);
            outWriter = new OutputStreamWriter(os);
            outWriter.write(strContent);
            outWriter.flush();
            return true;
        } catch (Exception ex) {
            Log.e("FileUtil", "setCacheData", ex);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (outWriter != null) {
                    outWriter.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static String getCacheDir(String strRoot, String uid) {
        String strDir = strRoot + "/data/" + uid;
        return strDir;
    }

    public static String getKXCacheDir(Context context) {
        if (context == null) {
            return null;
        }
        String strCacheDir = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File sdFile = ActivityUtils.getSDPath();
            strCacheDir = sdFile.getAbsolutePath() + "/images/cache";
        } else {
            strCacheDir = context.getCacheDir().getAbsolutePath();
        }
        return strCacheDir;
    }

    //目录复制
    public static int copy(String fromFile, String toFile) {
//要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
//如同判断SD卡是否存在或者文件是否存在
//如果不存在则 return出去
        if (!root.exists()) {
            return -1;
        }
//如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();

//目标目录
        File targetDir = new File(toFile);
//创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
//遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");

            } else//如果当前项为文件则进行文件拷贝
            {
                CopySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return 0;
    }


    //文件拷贝
//要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopySdcardFile(String fromFile, String toFile) {

        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }


	/*
     * public static void logMemInfo(String tag){ System.gc(); long total =
	 * Runtime.getRuntime().totalMemory(); long free =
	 * Runtime.getRuntime().freeMemory(); long used = total - free; Log.i(tag +
	 * " totalMemory", String.valueOf(total)); Log.i(tag + " freeMemory",
	 * String.valueOf(free)); Log.i(tag + " usedMemory", String.valueOf(used));
	 * Log.i(tag + " maxMemory",
	 * String.valueOf(Runtime.getRuntime().maxMemory())); }
	 */
}
