package com.demo.mb.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

/**
 * 生命周期检查工具类
 */
class LifecycleChecker : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onAppBackground() {
        // 应用进入后台
        Timber.e("LifecycleChecker onAppBackground ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForeground() {
        // 应用进入前台
        Timber.e("LifecycleChecker onAppForeground ON_START")
    }
}