package com.muban.net.api

import com.muban.net.model.BaseEntity
import okhttp3.OkHttpClient

/**
 * 网络请求类
 */
class CommonNet private constructor() : BaseNet() {

    private var service: CommonService = getService(CommonService::class.java, "")

    companion object {
        val instant: CommonNet by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CommonNet()
        }
    }

    /**
     * okHttp拓展
     */
    override fun handleBuilder(builder: OkHttpClient.Builder) {

    }

    suspend fun common(): BaseEntity<Any> {
        val args = HashMap<String, Any>()
        args["params"] = ""
        return service.common(args)
    }
}