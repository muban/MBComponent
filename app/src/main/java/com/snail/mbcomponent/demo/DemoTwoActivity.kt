package com.snail.mbcomponent.demo

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.muban.common.router.RouterPaths
import com.snail.mbcomponent.R
import com.snail.mbcomponent.custom.PosterView
import com.snail.mbcomponent.databinding.ActivityDemoTwoBinding

/**
 * Canvas练习
 */
@Route(path = RouterPaths.DEMO_TWO_ACTIVITY)
class DemoTwoActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityDemoTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_two)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo_two)
        dataBinding.lifecycleOwner = this
        progressLogic()
    }

    private fun progressLogic() {
        dataBinding.flDemoTwo.addView(
            PosterView(this),
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }
}