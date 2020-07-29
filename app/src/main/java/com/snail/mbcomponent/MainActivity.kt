package com.snail.mbcomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.muban.common.router.RouterPaths
import com.muban.net.api.CommonNet
import com.snail.mbcomponent.databinding.ActivityMainBinding
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.login = "Login !"
        dataBinding.registered = "Registered !"
        dataBinding.tvLogin.setOnClickListener {
            ARouter.getInstance().build(RouterPaths.LOGIN_ACTIVITY).navigation()
        }
        dataBinding.tvRegistered.setOnClickListener {
            ARouter.getInstance().build(RouterPaths.REGISTERED_ACTIVITY).navigation()
        }
        //协程请求
        lifecycleScope.launchWhenResumed {
            val common1 = async { CommonNet.instant.common() }
            val common2 = async { CommonNet.instant.common() }
            print(common1.await().toString() + common2.await().toString())
        }
    }
}