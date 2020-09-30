package com.muban.model.api

import com.muban.model.entity.BaseEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Service请求列表例子
 */
interface CommonService {
    @GET(ConstUrl.COMMON_URL)
    suspend fun common(@QueryMap body: Map<String, @JvmSuppressWildcards Any>): BaseEntity<Any>
}