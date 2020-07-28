package com.muban.net.model

/**
 * 数据接口，统一字段规范
 */
interface ModelInterface<Data> {
    fun code(): Int?
    fun msg(): String?
    fun data(): Data?
    fun isSuccess(): Boolean
}