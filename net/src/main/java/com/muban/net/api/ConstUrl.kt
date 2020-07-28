package com.muban.net.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull

interface ConstUrl{
    companion object{
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        //通用
        const val COMMON_URL = ""
    }
}