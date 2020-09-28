package com.muban.common.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import com.muban.common.net.HttpError
import es.dmoral.toasty.Toasty
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Activity基类
 */
abstract class BaseActivity<VM : BaseViewModel, VDB : ViewDataBinding>
    : SupportActivity(), View.OnClickListener {
    //
    protected var mViewModel: VM? = null
    protected lateinit var binding: VDB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局
        setContentView(getContentViewId())
        //初始化我们的binging
        binding = DataBindingUtil.setContentView(this, getContentViewId())
        //给binding加上感知生命周期
        binding.lifecycleOwner = this
        //创建我们的ViewModel。
        createViewModel()
        //绑定EventBus
        if (isUseEventBus()) {
            EventBus.getDefault().register(this)
        }
        //页面逻辑
        initView()
        initEvent()
        initData()
    }

    /**
     * 创建viewModel
     */
    abstract fun createViewModel()

    /**
     * 获取布局文件
     */
    abstract fun getContentViewId(): Int

    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 初始化监听
     */
    abstract fun initEvent()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {

    }

    /**
     * 是否使用eventBus
     */
    protected fun isUseEventBus() = true

    /**
     * 处理网络错误信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handlingHttpError(event: HttpError) {
        //判断当前页面是否可见
        if (lifecycle.currentState != Lifecycle.State.RESUMED) return
        //处理错误码
        Toasty.normal(this, event.errorMsg ?: "${event.code}").show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //解绑eventBus
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }
}