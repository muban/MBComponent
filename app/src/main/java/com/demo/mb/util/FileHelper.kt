package com.demo.mb.util

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment.*
import android.provider.MediaStore
import com.muban.common.CommonApplication
import java.io.BufferedInputStream
import java.io.File
import java.io.OutputStream


/**
 * 文件帮助类
 */
object FileHelper {

    /**
     * 下载的图片保存的位置
     */
    fun getPublickDiskImagePicDir(): String? {
        return getPublickDiskFileDir(CommonApplication.application, DIRECTORY_PICTURES)
    }


    /**
     * 电影保存的位置
     */
    fun getPublickDiskMoviesDir(): String? {
        return getPublickDiskFileDir(CommonApplication.application, DIRECTORY_MOVIES)
    }

    /**
     * 音乐保存的位置
     */
    fun getPublickDiskMusicDir(): String? {
        return getPublickDiskFileDir(CommonApplication.application, DIRECTORY_MUSIC)
    }

    /**
     * 创建保存图片的缓存目录
     */
    fun getPublickDiskImagePicCacheDir(): String? {
        return getPublickDiskCacheDir(CommonApplication.application, DIRECTORY_PICTURES)
    }

    /**
     * 创建保存文件的位置
     */
    fun getPublickDiskDownloadDir(): String? {
        return getPublickDiskCacheDir(CommonApplication.application, DIRECTORY_DOWNLOADS)
    }


    /**
     * 获取外部存储目录下缓存的fileName的文件夹路径
     */
    fun getPublickDiskCacheDir(context: Context, fileDir: String): String {
        val cachePath: String =
            if (MEDIA_MOUNTED == getExternalStorageState() || !isExternalStorageRemovable()) {
                //此目录下的是外部存储下的私有的fileName目录
                //SDCard/Android/data/你的应用包名/cache/fileName
                context.externalCacheDir?.path + "/" + fileDir
            } else {
                //data/data/com.my.app/files
                context.cacheDir.path + "/" + fileDir
            }
        //创建文件夹
        val file = File(cachePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }

    /**
     * 获取外部存储目录下的fileName的文件夹路径
     */
    fun getPublickDiskFileDir(context: Context, fileDir: String): String {
        val filePath: String =
            if (MEDIA_MOUNTED == getExternalStorageState() || !isExternalStorageRemovable()) {
                //此目录下的是外部存储下的私有的fileName目录
                //mnt/sdcard/Android/data/com.my.app/files/fileName
                context.getExternalFilesDir(fileDir)?.absolutePath!!
            } else {
                //data/data/com.my.app/files
                context.filesDir.path + "/" + fileDir
            }
        //创建文件夹
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }

    /**
     * 获取公共目录，注意，只适合android9.0以下的
     */
    fun getPublickDiskFileDirAndroid9(fileDir: String): String {
        val filePath = ""
        if (MEDIA_MOUNTED == getExternalStorageState() || !isExternalStorageRemovable()) {
            getExternalStoragePublicDirectory(fileDir).path
        }
        //创建文件夹
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }

    /**
     * 保存String到公共目录
     */
    fun savePageToPublickDiskDir(
        content: String,
        context: Context,
        fileName: String,
    ) {
        if (Build.VERSION.SDK_INT < 29) return
        try {
            val is_ = content.byteInputStream()
            val contentValues = ContentValues()
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            contentValues.put(MediaStore.Downloads.MIME_TYPE, "application/json")
            contentValues.put(MediaStore.Downloads.DATE_TAKEN, System.currentTimeMillis())
            val uri = context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                contentValues
            )
            val inputStream = BufferedInputStream(is_)
            val os: OutputStream? = context.contentResolver.openOutputStream(uri!!)
            if (os != null) {
                val buffer = ByteArray(1024)
                var len: Int
                var total = 0
                while (inputStream.read(buffer).also { len = it } != -1) {
                    os.write(buffer, 0, len)
                    total += len
                }
            }
            os?.flush()
            inputStream.close()
            is_.close()
            os?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}