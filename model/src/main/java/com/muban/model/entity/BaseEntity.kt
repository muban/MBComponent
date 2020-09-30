package com.muban.model.entity

/**
 * 数据基类
 */
data class BaseEntity<Data>(
    var code: Int = 0,
    var msg: String = "",
    var data: Data? = null
) : EntityInterface<Data> {

    override fun code(): Int? = code

    override fun msg(): String? = msg

    override fun data(): Data? = data

    override fun isSuccess(): Boolean = code == 0

}