package com.demo.mb.view.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityAopBinding
import com.demo.mb.viewmodel.DemoViewModel
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths

/**
 * AOP练习
 */
@Route(path = RouterPaths.AopActivity)
class AopActivity : BaseActivity<DemoViewModel, ActivityAopBinding>() {
    override fun getContentViewId(): Int = R.layout.activity_aop

    override fun createViewModel() {

    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

}