package com.muban.model.api

import com.muban.model.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求封装基类
 */
abstract class BaseNet {

    companion object {
        const val TIME_OUT = 5L
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.addInterceptor(logging)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            handleBuilder(builder)
            return builder.build()
        }

    /**
     * 方便子类扩展
     */
    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    open fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}