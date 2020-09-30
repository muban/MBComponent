package com.muban.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.luck.picture.lib.config.PictureSelectionConfig
import com.muban.common.util.GlideEngine
import com.tencent.mmkv.MMKV
import timber.log.Timber
import timber.log.Timber.DebugTree

abstract class CommonApplication : Application() {

    companion object {
        lateinit var application: CommonApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        initRouter()
        init()
    }

    /**
     * 初始化路由器
     */
    private fun initRouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            //打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    /**
     * 初始化三方库
     */
    private fun init() {
        //字段存储
        MMKV.initialize(this)
        //日志打印
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        //设置图片加载器
        PictureSelectionConfig.imageEngine = GlideEngine.createGlideEngine()
    }
}