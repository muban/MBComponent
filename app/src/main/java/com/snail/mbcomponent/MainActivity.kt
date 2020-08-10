package com.snail.mbcomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.muban.common.router.RouterPaths
import com.snail.mbcomponent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.login = "Login !"
        dataBinding.registered = "Registered !"
        dataBinding.demo1 = "Demo-LayoutManager"
        dataBinding.demo2 = "Demo-Canvas"
        dataBinding.tvLogin.setOnClickListener {
            ARouter.getInstance().build(RouterPaths.LOGIN_ACTIVITY).navigation()
        }
        dataBinding.tvRegistered.setOnClickListener {
            ARouter.getInstance().build(RouterPaths.REGISTERED_ACTIVITY).navigation()
        }
        dataBinding.btnDemo1.setOnClickListener {
            ARouter.getInstance().build(RouterPaths.DEMO_ONE_ACTIVITY).navigation()
        }
        dataBinding.btnDemo2.setOnClickListener {
            ARouter.getInstance().build(RouterPaths.DEMO_TWO_ACTIVITY).navigation()
        }
        //协程请求
//        lifecycleScope.launchWhenResumed {
//            val common1 = async { CommonNet.instant.common() }
//            val common2 = async { CommonNet.instant.common() }
//            print(common1.await().toString() + common2.await().toString())
//        }
    }
}