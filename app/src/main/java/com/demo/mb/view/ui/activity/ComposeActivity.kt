package com.demo.mb.view.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.mb.R
import com.muban.common.router.RouterPaths

@Route(path = RouterPaths.ComposeActivity)
class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
    }
}