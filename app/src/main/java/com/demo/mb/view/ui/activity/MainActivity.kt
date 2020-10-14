package com.demo.mb.view.ui.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.mb.R
import com.demo.mb.databinding.ActivityMainBinding
import com.demo.mb.view.ui.adapter.SimpleAdapter
import com.demo.mb.viewmodel.MainViewModel
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    SimpleAdapter.ItemClickListener {

    //练习列表
    private val mList = mutableListOf<String>()
    private lateinit var mAdapter: SimpleAdapter

    override fun getContentViewId(): Int = R.layout.activity_main

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun initView() {
        initRecycler()
    }

    private fun initRecycler() {
        mList.add("MotionLayout")
        //
        mAdapter = SimpleAdapter(mList)
        binding.rvDemo.layoutManager = LinearLayoutManager(this)
        binding.rvDemo.adapter = mAdapter
    }

    override fun initEvent() {
        mAdapter.mListener = this
    }

    override fun initData() {
    }

    /**
     * 功能点击
     */
    override fun itemClick(position: Int) {
        when (position) {
            0 -> ARouter.getInstance().build(RouterPaths.ACTIVITY_MOTION).navigation()
        }
    }
}