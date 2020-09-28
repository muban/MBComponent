package com.muban.net.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull

/**
 * api请求地址例子
 */
interface ConstUrl{
    companion object{
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        //通用
        const val COMMON_URL = ""
    }
}