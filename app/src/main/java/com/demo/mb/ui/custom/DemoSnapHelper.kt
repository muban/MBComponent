package com.demo.mb.ui.custom

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class DemoSnapHelper : SnapHelper() {
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        TODO("Not yet implemented")
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        TODO("Not yet implemented")
    }

}