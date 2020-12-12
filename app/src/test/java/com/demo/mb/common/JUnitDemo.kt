package com.demo.mb.common

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * 单元测试demo
 * 一个测试类单元测试的执行顺序为： @BeforeClass –> @Before –> @Test –> @After –> @AfterClass
 */
@RunWith(JUnit4::class)
class JUnitDemo {
    //    @Test
    //    标记该方法为测试方法。测试方法必须是public void，可以抛出异常。
    //    @RunWith
    //    指定一个Runner来提供测试代码运行的上下文环境。（Runner的概念）
    //    @Rule
    //    定义测试类中的每个测试方法的行为，比如指定某个Activity作为测试运行的上下文。
    //    @Before
    //    初始化方法，通常进行测试前置条件的设置。
    //    @After
    //    释放资源，它会在每个测试方法执行完后都调用一次。
    @Before
    fun before() {

    }

    @Test
    fun test() {

    }

    @After
    fun after() {

    }
}