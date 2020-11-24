package com.demo.mb.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.demo.mb.util.hilt.Repository
import com.muban.common.base.BaseViewModel

class HiltViewModel @ViewModelInject constructor(
    application: Application,
    val repository: Repository
) : BaseViewModel(application) {

    fun test() {
        println("test HiltViewModel")
        println("retrofit=${repository.retrofit}")
    }
}