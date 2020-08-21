package com.snail.mbcomponent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snail.mbcomponent.R
import com.snail.mbcomponent.databinding.ItemSimpleBinding

class SimpleAdapter(data: MutableList<String>) : RecyclerView.Adapter<CommonViewHolder>() {
    private lateinit var context: Context
    private val mData = mutableListOf<String>()

    //
    var mListener: ItemClickListener? = null

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
        val view = LayoutInflater.from(context).inflate(R.layout.item_simple, parent, false)
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val dataBinding = holder.dataBinding as ItemSimpleBinding
        dataBinding.tvItemSimple.text = mData[position]
        dataBinding.tvItemSimple.setOnClickListener {
            mListener?.itemClick(position)
        }
    }

    interface ItemClickListener {
        fun itemClick(position: Int)
    }
}