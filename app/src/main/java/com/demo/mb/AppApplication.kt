package com.demo.mb

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import com.demo.mb.util.LifecycleChecker
import com.muban.common.CommonApplication

class AppApplication : CommonApplication(), Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        //注册activity生命周期监听回调
        registerActivityLifecycleCallbacks(this)
        //监听app后台与前台
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleChecker())
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}