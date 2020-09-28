package com.muban.common.router

/**
 * 数据传递时使用的key值
 */
class RouterKey {
    companion object {
        const val ID = "ID"
        fun id(flag: Int) = "${ID}${flag}"
        const val TYPE = "TYPE"
        const val TITLE = "TITLE"
        const val COUNT = "COUNT"
        const val BOOBLE = "BOOBLE"
        const val DATA = "DATA"
        fun data(flag: Int) = "${DATA}${flag}"
    }
}