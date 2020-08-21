package com.snail.mbcomponent.demo

import android.graphics.Path
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.muban.common.router.RouterPaths
import com.snail.mbcomponent.R
import com.snail.mbcomponent.adapter.DemoOneAdapter
import com.snail.mbcomponent.databinding.ActivityDemoOneBinding
import com.snail.mbcomponent.pathlayoutmanager.PathLayoutManager
import com.snail.mbcomponent.util.ScreenUtils

/**
 * layoutManager练习
 */
@Route(path = RouterPaths.DEMO_ONE_ACTIVITY)
class DemoOneActivity : AppCompatActivity() {

    private val mList = mutableListOf<String>()
    private lateinit var mAdapter: DemoOneAdapter

    //
    private lateinit var dataBinding: ActivityDemoOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_one)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo_one)
        dataBinding.lifecycleOwner = this
        progressLogic()
    }

    private fun progressLogic() {
        //初始化列表
        for (i in 0 until 10) {
            mList.add("index:${i}")
        }
        //
        val path = Path()
        val screenH = ScreenUtils.getScreen_h(this)
        val screenW = ScreenUtils.getScreen_w(this)
        path.moveTo(0f, screenH / 2f)
        path.rLineTo(screenW.toFloat(), 0f)
        //
        mAdapter = DemoOneAdapter(mList)
//        dataBinding.rvDemoOne.layoutManager =
//            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val pathLayoutManager = PathLayoutManager(
            path,
            (120 * ScreenUtils.getDensity(this)).toInt(),
            RecyclerView.HORIZONTAL
        )
        pathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_LOOP)
        dataBinding.rvDemoOne.layoutManager = pathLayoutManager
        dataBinding.rvDemoOne.adapter = mAdapter
    }
}