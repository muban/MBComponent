package com.demo.mb.view.ui.activity

import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityComposeBinding
import com.demo.mb.view.ui.screen.ComposeHomeScreen
import com.demo.mb.viewmodel.DemoViewModel
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths

@Route(path = RouterPaths.ComposeActivity)
class ComposeActivity : BaseActivity<DemoViewModel, ActivityComposeBinding>() {

    override fun getContentViewId(): Int = R.layout.activity_compose

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(DemoViewModel::class.java)
    }

    override fun initView() {
        setContent {
            ComposeHomeScreen()
        }
    }

    override fun initEvent() {
    }

    override fun initData() {
    }
}