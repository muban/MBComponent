package com.demo.mb.ui.activity

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.mb.R
import com.demo.mb.databinding.ActivityMainBinding
import com.demo.mb.ui.adapter.SimpleAdapter
import com.demo.mb.ui.viewmodel.MainViewModel
import com.muban.common.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    SimpleAdapter.ItemClickListener {

    //练习列表
    private val mList = mutableListOf<String>()
    private lateinit var mAdapter: SimpleAdapter

    override fun getContentViewId(): Int = R.layout.activity_main

    override fun createViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun initView() {
        initRecycler()
    }

    private fun initRecycler() {
        repeat(5) {
            mList.add("学习目标$it")
        }
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

    }
}