package com.demo.mb.view.ui.activity

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityFileBinding
import com.demo.mb.model.entity.PageValue
import com.demo.mb.util.FileHelper
import com.demo.mb.viewmodel.FileViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths
import es.dmoral.toasty.Toasty
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

/**
 * file沙盒文件练习
 */
@Route(path = RouterPaths.FileActivity)
class FileActivity : BaseActivity<FileViewModel, ActivityFileBinding>() {
    override fun getContentViewId(): Int = R.layout.activity_file

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(FileViewModel::class.java)
    }

    override fun initView() {
    }

    override fun initEvent() {
        binding.tvFileIos.setOnClickListener {
            transformIosPage()
        }
        binding.tvFileAndroid.setOnClickListener {
            transformAndroidPage()
        }
    }

    override fun initData() {

    }

    /**
     * 转换ios页面信息
     */
    @SuppressLint("NewApi")
    private fun transformIosPage() {
        try {
            //获取映射表
            val pageJson = StringBuilder()
            val inputReader = InputStreamReader(application.resources.assets.open("pages.json"))
            val bufReader = BufferedReader(inputReader)
            var line: String?
            while (bufReader.readLine().also { line = it } != null) {
                pageJson.append(line)
            }
            //获取页面映射表
            val pageMap = Gson().fromJson<Map<String, PageValue>>(
                pageJson.toString(),
                object : TypeToken<HashMap<String?, PageValue?>?>() {}.type
            )
            val iosPageMap = HashMap<String, PageValue>()
            pageMap?.forEach { t, u ->
                val iosPage = iosPageMap[u.ios]
                if (iosPage != null) {
                    iosPage.title = "${iosPage.title}/${u.title}"
                    iosPage.titleId = "${iosPage.titleId}/${u.titleId}"
                    iosPage.android = "${iosPage.android}/${u.android}"
                } else {
                    iosPageMap[u.ios] = u
                }
            }
            val iosPageJson = Gson().toJson(iosPageMap)
            FileHelper.savePageToPublickDiskDir(iosPageJson.toString(), this, "page_ios.json")
            Toasty.normal(this, "success").show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toasty.normal(this, "error").show()
        }
    }

    /**
     * 转换android页面信息
     */
    @SuppressLint("NewApi")
    private fun transformAndroidPage() {
        try {
            //获取映射表
            val pageJson = StringBuilder()
            val inputReader = InputStreamReader(application.resources.assets.open("pages.json"))
            val bufReader = BufferedReader(inputReader)
            var line: String?
            while (bufReader.readLine().also { line = it } != null) {
                pageJson.append(line)
            }
            //获取页面映射表
            val pageMap = Gson().fromJson<Map<String, PageValue>>(
                pageJson.toString(),
                object : TypeToken<HashMap<String?, PageValue?>?>() {}.type
            )
            val iosPageMap = HashMap<String, PageValue>()
            pageMap?.forEach { t, u ->
                val androidPage = iosPageMap[u.android]
                if (androidPage != null) {
                    androidPage.title = "${androidPage.title}/${u.title}"
                    androidPage.titleId = "${androidPage.titleId}/${u.titleId}"
                    androidPage.ios = "${androidPage.ios}/${u.ios}"
                } else {
                    iosPageMap[u.android] = u
                }
            }
            val androidPageJson = Gson().toJson(iosPageMap)
            FileHelper.savePageToPublickDiskDir(androidPageJson.toString(), this, "page_android.json")
            Toasty.normal(this, "success").show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toasty.normal(this, "error").show()
        }
    }
}