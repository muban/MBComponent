package com.muban.net.api

import com.muban.net.model.BaseEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Service请求列表例子
 */
interface CommonService {
    @GET(ConstUrl.COMMON_URL)
    suspend fun common(@QueryMap body: Map<String, @JvmSuppressWildcards Any>): BaseEntity<Any>
}