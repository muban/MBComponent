package com.demo.mb.view.ui.activity

import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityHiltBinding
import com.demo.mb.util.hilt.Truck
import com.demo.mb.viewmodel.HiltViewModel
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * hilt依赖注入demo
 */
@AndroidEntryPoint
@Route(path = RouterPaths.HiltActivity)
class HiltActivity : BaseActivity<HiltViewModel, ActivityHiltBinding>() {

    @Inject
    lateinit var truck: Truck

    override fun getContentViewId(): Int = R.layout.activity_hilt

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(HiltViewModel::class.java)
    }

    override fun initView() {
        truck.go()
        mViewModel?.test()
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

}