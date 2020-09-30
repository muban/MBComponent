package com.muban.model.api

import com.muban.model.entity.BaseEntity
import okhttp3.OkHttpClient

/**
 * 网络请求类例子
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