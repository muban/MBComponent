package com.demo.mb.view.ui.activity

import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityMaterialSecondDemoBinding
import com.demo.mb.viewmodel.DemoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.muban.common.CommonApplication
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths
import es.dmoral.toasty.Toasty

@Route(path = RouterPaths.MaterialDemoSecondActivity)
class MaterialDemoSecondActivity :
    BaseActivity<DemoViewModel, ActivityMaterialSecondDemoBinding>() {

    override fun getContentViewId(): Int = R.layout.activity_material_second_demo

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(DemoViewModel::class.java)
    }

    override fun initView() {

    }

    override fun initEvent() {
        binding.cardMsd.setOnClickListener {
            showMaterialDialog()
        }
    }

    override fun initData() {
    }

    private fun showMaterialDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Title")
            .setMessage("Message")
            .setNegativeButton("cancel") { dialog, which ->
                Toasty.normal(CommonApplication.application, "cancel").show()
            }
            .setPositiveButton("submit") { dialog, which ->
                Toasty.normal(CommonApplication.application, "submit").show()
            }
            .show()
    }
}