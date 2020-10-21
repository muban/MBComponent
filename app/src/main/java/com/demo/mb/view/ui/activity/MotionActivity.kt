package com.demo.mb.view.ui.activity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityMotionBinding
import com.demo.mb.viewmodel.DemoViewModel
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths
import es.dmoral.toasty.Toasty

/**
 * MotionLayout demo
 */
@Route(path = RouterPaths.MotionActivity)
class MotionActivity : BaseActivity<DemoViewModel, ActivityMotionBinding>() {

    override fun getContentViewId(): Int = R.layout.activity_motion

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(DemoViewModel::class.java)
    }

    override fun initView() {

    }

    override fun initEvent() {
        binding.zero.setOnClickListener(this)
        binding.planet1.setOnClickListener(this)
        binding.planet2.setOnClickListener(this)
        binding.planet3.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        v?.run {
            when (this) {
                binding.zero -> {
                    Toasty.normal(this@MotionActivity, "binding.zero").show()
                }
                binding.planet1 -> {
                    Toasty.normal(this@MotionActivity, "binding.planet1").show()
                }
                binding.planet2 -> {
                    Toasty.normal(this@MotionActivity, "binding.planet2").show()
                }
                binding.planet3 -> {
                    Toasty.normal(this@MotionActivity, "binding.planet3").show()
                }
                else -> {
                }
            }
        }
    }
}