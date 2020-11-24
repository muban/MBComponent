package com.demo.mb.view.ui.activity

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.demo.mb.databinding.ActivityComposeBinding
import com.demo.mb.viewmodel.DemoViewModel
import com.muban.common.base.BaseActivity
import com.muban.common.router.RouterPaths
import es.dmoral.toasty.Toasty

@Route(path = RouterPaths.ComposeActivity)
class ComposeActivity : BaseActivity<DemoViewModel, ActivityComposeBinding>() {

    override fun getContentViewId(): Int = R.layout.activity_compose

    override fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(DemoViewModel::class.java)
    }

    override fun initView() {
        spanTest()
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    private fun spanTest() {
        val content = "阿撒上的凯撒奖的卡拉斯京的卡上了肯德基卢萨卡就到了卡睡觉了的摩卡色的卡上，《隐私协议》撒都不接啊时刻都可能萨里看到你啦剋深南东路"
        val key = "《隐私协议》"
        val start = content.indexOf(key)
        val end = start + key.length
        val span = SpannableString(content)
        val clickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toasty.normal(this@ComposeActivity, "click").show()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = Color.parseColor("#00ff00")
                ds.isUnderlineText = false
            }
        }
        span.setSpan(clickSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        //
        binding.tvContent.text = span
        binding.tvContent.movementMethod = LinkMovementMethod.getInstance()
        binding.tvContent.highlightColor = Color.parseColor("#00000000")
    }
}