package com.muban.net.model

/**
 * 数据基类
 */
data class BaseModel<Data>(
    var code: Int = 0,
    var msg: String = "",
    var data: Data? = null
) : ModelInterface<Data> {

    override fun code(): Int? = code

    override fun msg(): String? = msg

    override fun data(): Data? = data

    override fun isSuccess(): Boolean = code == 0

}