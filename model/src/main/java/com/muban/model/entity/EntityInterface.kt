package com.muban.model.entity

/**
 * 数据接口，统一字段规范
 */
interface EntityInterface<Data> {
    fun code(): Int?
    fun msg(): String?
    fun data(): Data?
    fun isSuccess(): Boolean
}