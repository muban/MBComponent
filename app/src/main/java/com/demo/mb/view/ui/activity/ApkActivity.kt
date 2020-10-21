package com.demo.mb.view.ui.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityApkBinding
import com.demo.mb.model.entity.ApkEntity
import com.demo.mb.view.ui.adapter.ApkAdapter
import com.demo.mb.viewmodel.DemoViewModel
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths
import java.io.File


@Route(path = RouterPaths.ACTIVITY_APK)
class ApkActivity : BaseActivity<DemoViewModel, ActivityApkBinding>(),
    ApkAdapter.ItemClickListener {

    private val mList = mutableListOf<ApkEntity>()
    private lateinit var mAdapter: ApkAdapter

    override fun getContentViewId(): Int = R.layout.activity_apk

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(DemoViewModel::class.java)
    }

    override fun initView() {
        initRecycler()
    }

    private fun initRecycler() {
        mAdapter = ApkAdapter(mList)
        binding.rvApk.layoutManager = LinearLayoutManager(this)
        binding.rvApk.adapter = mAdapter
    }

    override fun initEvent() {
        mAdapter.mListener = this
    }

    override fun initData() {
        // 获取已安装应用列表
        val apps = packageManager.getInstalledApplications(0)
        apps.forEach {
            // 获取apk地址
            if (it.sourceDir?.startsWith("/data/app") == true) {
                val length = File(it.sourceDir).length()
                val size = length * 1f / 1024 / 1024
                mList.add(
                    ApkEntity(
                        name = "应用：${it.loadLabel(packageManager)}",
                        size = "大小：${size}M",
                        path = it.sourceDir,
                        icon = it.loadIcon(packageManager),
                        info = it
                    )
                )
            }
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun itemClick(position: Int, entity: ApkEntity) {
        //{".apk", "application/vnd.android.package-archive"}
        //todo 发送安装包
    }
}