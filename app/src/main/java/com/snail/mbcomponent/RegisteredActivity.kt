package com.snail.mbcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.muban.common.router.RouterPaths

@Route(path = RouterPaths.REGISTERED_ACTIVITY)
class RegisteredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registered)
    }
}