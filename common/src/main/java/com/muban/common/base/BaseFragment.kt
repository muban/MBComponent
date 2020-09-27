package com.muban.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import me.yokeyword.fragmentation.SupportFragment

/**
 * Fragment基类
 */
abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding>
    : SupportFragment(), View.OnClickListener {
    //
    protected var mViewModel: VM? = null
    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //初始化我们的binging
        binding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
        //给binding加上感知生命周期
        binding.lifecycleOwner = this
        //创建我们的ViewModel。
        createViewModel()
        //设置布局
        return binding.root
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
        initEvent()
        initData()
    }

    /**
     * 获取布局文件
     */
    abstract fun getContentViewId(): Int

    /**
     * 创建viewModel
     */
    abstract fun createViewModel()

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
}