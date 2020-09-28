package com.muban.net.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * header拦截器例子
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        //todo add header

        //打印头部
        val request = builder.build()
        val headers = StringBuilder()
        for (name in request.headers.names()) {
            headers.append(name).append(":").append(request.header(name)).append("\n")
        }
        Log.i("net", "<>\n------headers------\n$headers------headers------")
        return chain.proceed(builder.build())
    }
}