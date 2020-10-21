package com.demo.mb.view.ui.activity

import android.transition.TransitionManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.mb.R
import com.demo.mb.databinding.ActivityMaterialDemoBinding
import com.demo.mb.viewmodel.DemoViewModel
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.platform.MaterialFade
import com.muban.common.CommonApplication
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths
import es.dmoral.toasty.Toasty

@Route(path = RouterPaths.MaterialDemoActivity)
class MaterialDemoActivity : BaseActivity<DemoViewModel, ActivityMaterialDemoBinding>() {

    override fun getContentViewId(): Int = R.layout.activity_material_demo

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(DemoViewModel::class.java)
    }

    override fun initView() {
        binding.button = "jump to material second"
        //设置样式
        binding.sivMaterialDemo.shapeAppearanceModel = ShapeAppearanceModel.Builder()
            //设置圆形图
            .setAllCornerSizes(ShapeAppearanceModel.PILL)
            .build()
        //设置底部菜单栏小红点
        binding.bnvMaterialDemo.getOrCreateBadge(R.id.page_1)?.apply {
            isVisible = true
        }
        binding.bnvMaterialDemo.getOrCreateBadge(R.id.page_2)?.apply {
            isVisible = true
            number = 99
        }
        binding.bnvMaterialDemo.getOrCreateBadge(R.id.page_3)?.apply {
            isVisible = true
            number = 999
        }
        binding.bnvMaterialDemo.getOrCreateBadge(R.id.page_4)?.apply {
            isVisible = true
            number = 9999
        }
    }

    override fun initEvent() {
        binding.mbMaterialDemoL.setOnClickListener {
            //设置过渡动画
            val fade = MaterialFade()
            TransitionManager.beginDelayedTransition(binding.clMaterialDemo, fade)
            binding.sivMaterialDemo.run {
                visibility = if (visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
        binding.mbMaterialDemo.setOnClickListener {
            //跳转新页面，跳转动画
            ARouter.getInstance().build(RouterPaths.MaterialDemoSecondActivity)
//                .withOptionsCompat(
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        this@MaterialDemoActivity, binding.mbMaterialDemo, "jump"
//                    )
//                )
                .navigation()
        }
        //底部菜单点击监听
        binding.bnvMaterialDemo.setOnNavigationItemSelectedListener {
            //移除小红点
            binding.bnvMaterialDemo.removeBadge(it.itemId)
            //
            Toasty.normal(CommonApplication.application, it.title).show()
            true
        }
    }

    override fun initData() {
        //旋转动画
        binding.sivMaterialDemo.animation = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            interpolator = LinearInterpolator()
            duration = 10000
            repeatCount = -1
            fillAfter = false
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.sivMaterialDemo.clearAnimation()
    }
}