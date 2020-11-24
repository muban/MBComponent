package com.demo.mb.util.hilt

import javax.inject.Inject

/**
 * hilt demo ，通过@Inject注解标识可以通过该构造函数提供实例
 */
class Truck @Inject constructor(var driver: Driver) {

    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine

    fun go() {
        gasEngine.start()
        electricEngine.start()
        println("Truck is delivering cargo. Driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }
}