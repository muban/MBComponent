package com.snail.mbcomponent.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.muban.common.router.RouterPaths
import com.snail.mbcomponent.R
import com.snail.mbcomponent.adapter.SimpleAdapter
import com.snail.mbcomponent.databinding.ActivityLoginBinding
import es.dmoral.toasty.Toasty

@Route(path = RouterPaths.LOGIN_ACTIVITY)
class LoginActivity : AppCompatActivity(), SimpleAdapter.ItemClickListener {
    lateinit var dataBinding: ActivityLoginBinding

    //
    private val mList = mutableListOf<String>()
    private lateinit var mAdapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dataBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        dataBinding.lifecycleOwner = this
        progressLogic()
    }

    private fun progressLogic() {
        mList.apply {
            add("success toast")
            add("info toast")
            add("warning toast")
            add("normal toast")
            add("error toast")
        }
        mAdapter = SimpleAdapter(mList)
        dataBinding.rvLogin.layoutManager = LinearLayoutManager(this)
        dataBinding.rvLogin.adapter = mAdapter
        //
        mAdapter.mListener = this
    }

    override fun itemClick(position: Int) {
        when (position) {
            0 -> {
                Toasty.success(this, "success toast").show()
            }
            1 -> {
                Toasty.info(this, "info toast").show()
            }
            2 -> {
                Toasty.warning(this, "warning toast").show()
            }
            3 -> {
                Toasty.normal(this, "normal toast").show()
            }
            4 -> {
                Toasty.error(this, "error toast").show()
            }
        }
    }
}