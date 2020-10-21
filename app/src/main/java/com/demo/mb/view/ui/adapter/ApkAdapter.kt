package com.demo.mb.view.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.mb.R
import com.demo.mb.databinding.ItemApkBinding
import com.demo.mb.model.entity.ApkEntity
import com.muban.common.util.GlideApp

class ApkAdapter(data: MutableList<ApkEntity>) : RecyclerView.Adapter<CommonViewHolder>() {
    private lateinit var context: Context
    private var mData = mutableListOf<ApkEntity>()

    //
    var mListener: ItemClickListener? = null

    init {
        mData = data
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount(): Int = mData.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_apk, parent, false)
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val dataBinding = holder.dataBinding as ItemApkBinding
        val item = mData[position]
        //
        dataBinding.tvItemApkName.text = item.name
        dataBinding.tvItemApkSize.text = item.size
        dataBinding.tvItemApkPath.text = item.path
        //
        GlideApp.with(context).load(item.icon).into(dataBinding.ivItemApkIcon)
        //
        dataBinding.clItemApk.setOnClickListener {
            mListener?.itemClick(position, item)
        }
    }

    interface ItemClickListener {
        fun itemClick(position: Int, entity: ApkEntity)
    }
}