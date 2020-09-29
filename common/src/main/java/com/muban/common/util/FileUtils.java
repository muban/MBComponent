package com.muban.common.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.luck.picture.lib.tools.PictureFileUtils;
import com.muban.common.CommonApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件操作工具类（统一处理文件读取与文件写入）
 * Created by super on 2017/7/5.
 * update by muban on 2020/6/17
 */
public class FileUtils {
    //相册存储目录
    public static final String CAMERA_PATH = String.format("%s%s%s"
            , getExternalFilesDir(CommonApplication.application, Environment.DIRECTORY_DCIM).getAbsolutePath()
            , File.separator
            , "Camera");
    //下载目录
    public static final String DOWNLOAD_PATH = String.format("%s%s%s"
            , getExternalFilesDir(CommonApplication.application, Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
            , File.separator
            , "snail");

    //保存成功的文件地址
    public static String mFilePath;

    /**
     * 获取私有文件夹下载目录
     */
    public static File getSnailDir(Context context) {
        return getExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 获取私有目录下的文件夹
     */
    public static File getExternalFilesDir(Context context, String type) {
        File dirs = context.getExternalFilesDir(type);
        if (dirs != null)
            return dirs;
        return context.getFilesDir();
    }

    /**
     * 判断文件是否存在
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 保存图片
     *
     * @param context  上下文对象
     * @param bitmap   图片资源
     * @param fileName 图片名称
     * @return true：保存成功 false：保存失败
     */
    public static Boolean savePictureToCard(Context context, Bitmap bitmap, final String fileName) {
        boolean isSaveSuccess = false;
        //如果没有文件夹则创建
        File dir = getExternalFilesDir(context, Environment.DIRECTORY_PICTURES);
        File file = new File(dir, fileName);
        //判断Android 10版本
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            //Android版本低于10的图片存储
            BufferedOutputStream bos = null;
            try {
                //保存图片至本地
                String filePath = file.getAbsolutePath();
                bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                //通知相册刷新
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                context.sendBroadcast(intent);
                isSaveSuccess = true;
                //设置保存地址
                mFilePath = filePath;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //Android版本10以上的图片转存到共有目录
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DESCRIPTION, fileName);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.TITLE, "Image.jpg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + "Camera");
            //插入系统相册
            Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver resolver = context.getContentResolver();
            Uri insertUri = resolver.insert(external, values);
            //将图片写入文件中
            OutputStream os = null;
            try {
                if (insertUri != null) {
                    os = resolver.openOutputStream(insertUri);
                    mFilePath = PictureFileUtils.getPath(context, insertUri);
                }
                if (os != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                }
                isSaveSuccess = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSaveSuccess;
    }
}
