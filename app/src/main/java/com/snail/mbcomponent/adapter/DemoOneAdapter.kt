package com.snail.mbcomponent.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snail.mbcomponent.R
import com.snail.mbcomponent.databinding.ItemDemoOneBinding
import kotlin.random.Random

class DemoOneAdapter(data: MutableList<String>) : RecyclerView.Adapter<CommonViewHolder>() {
    private lateinit var context: Context
    private val mData = mutableListOf<String>()

    init {
        mData.clear()
        mData.addAll(data)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount(): Int = mData.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_demo_one, parent, false)
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val dataBinding = holder.dataBinding as ItemDemoOneBinding
        //todo 数据绑定
        dataBinding.ivItemDemoOne.setBackgroundColor(Color.rgb(Random.nextInt(255),Random.nextInt(255),Random.nextInt(255)))
    }
}