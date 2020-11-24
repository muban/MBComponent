package com.demo.mb.util.hilt

import javax.inject.Qualifier

@Qualifier//Qualifier注解的作用就是专门用于解决我们目前碰到的问题，给相同类型的类或接口注入不同的实例
@Retention(AnnotationRetention.BINARY)//AnnotationRetention.BINARY表示该注解在编译之后会得到保留,但是无法通过反射去访问这个注解
annotation class BindElectricEngine