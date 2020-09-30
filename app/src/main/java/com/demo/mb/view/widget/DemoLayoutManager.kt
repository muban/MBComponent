package com.demo.mb.view.widget

import androidx.recyclerview.widget.RecyclerView

class DemoLayoutManager : LearnLayoutManager() {

    private var mHasChild = false
    private var mItemViewHeight = 0//item高度
    private var mItemViewWidth = 0//item宽度
    private var mItemCount = 0//item数量
    private var mScrollOffset = 0//滑动偏移量

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler == null || state == null) return
        //recycler的item数量为0则返回不操作
        //跳过preLayout，preLayout主要用于支持动画
        if (state.itemCount == 0 || state.isPreLayout) return
        //将所有item的holder放入mRecyclerPool.mScrap缓存中
        removeAndRecycleAllViews(recycler)
        if (!mHasChild) {
            //todo 计算item宽高
            mHasChild = true
        }
        mItemCount = itemCount
        mScrollOffset = makeScrollOffsetWithinRange(mScrollOffset)
        //计算布局
        fill(recycler)
    }

    /**
     * 调整滑动偏移量
     */
    private fun makeScrollOffsetWithinRange(scrollOffset: Int): Int {
        //coerceAtLeast:左侧至少为右侧的值，取更大的一个（1.coerceAtLeast(2)=2）->对应Math.max
        //coerceAtMost:左侧最大为右侧的值，取更小的一个（2.coerceAtMost(1)=1）->对应Math.min
        return mItemViewWidth.coerceAtLeast(scrollOffset).coerceAtMost(mItemCount * mItemViewWidth)
    }

    /**
     * 填充布局
     */
    private fun fill(recycler: RecyclerView.Recycler) {
        // 1.初始化基本变量
        //最后一个可见item的position
        var bottomVisiblePosition = mScrollOffset / mItemViewWidth
        val bottomItemVisibleSize = mScrollOffset % mItemViewWidth
        //滑动的百分比，从1.0f~0.0f变化
        val offsetPercent = bottomItemVisibleSize * 1f / mItemViewWidth
//        val space = getHorizontalSpace()
        val space = 0
        var remainSpace = space
        //每个ItemView偏移的值(默认所有的ItemView都是左对齐)
        val defaultOffset = mItemViewWidth / 2
    }
}