package com.snail.mbcomponent.adapter

import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CommonViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
    var dataBinding: ViewDataBinding? = null

    init {
        dataBinding = DataBindingUtil.bind(itemView)
    }
}