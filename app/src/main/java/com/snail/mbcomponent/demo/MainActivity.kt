package com.snail.mbcomponent.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.muban.common.router.RouterPaths
import com.snail.mbcomponent.R
import com.snail.mbcomponent.adapter.SimpleAdapter
import com.snail.mbcomponent.databinding.ActivityMainBinding
import com.tencent.mmkv.MMKV
import es.dmoral.toasty.Toasty
import timber.log.Timber

class MainActivity : AppCompatActivity(), SimpleAdapter.ItemClickListener {
    lateinit var dataBinding: ActivityMainBinding

    //练习列表
    private val mList = mutableListOf<String>()
    private lateinit var mAdapter: SimpleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        dataBinding.lifecycleOwner = this
        progressLogic()
        //协程请求
//        lifecycleScope.launchWhenResumed {
//            val common1 = async { CommonNet.instant.common() }
//            val common2 = async { CommonNet.instant.common() }
//            print(common1.await().toString() + common2.await().toString())
//        }
    }

    private fun progressLogic() {
        mList.apply {
            add("ARouter 跳转")
            add("自定义LayoutManager学习")
            add("Canvas学习")
        }
        mAdapter = SimpleAdapter(mList)
        dataBinding.rvDemo.layoutManager = LinearLayoutManager(this)
        dataBinding.rvDemo.adapter = mAdapter
        //
        mAdapter.mListener = this
    }

    /**
     * 功能点击
     */
    override fun itemClick(position: Int) {
        when (position) {
            0 -> {
                ARouter.getInstance().build(RouterPaths.LOGIN_ACTIVITY).navigation()
                val kv = MMKV.defaultMMKV()
                kv.encode("test", "save test")
                Timber.e(kv.decodeString("test", "test error"))
            }
            1 -> {
                ARouter.getInstance().build(RouterPaths.DEMO_ONE_ACTIVITY).navigation()
            }
            2 -> {
                ARouter.getInstance().build(RouterPaths.DEMO_TWO_ACTIVITY).navigation()
            }
            else -> {
                Toasty.error(this, "out").show()
            }
        }
    }
}