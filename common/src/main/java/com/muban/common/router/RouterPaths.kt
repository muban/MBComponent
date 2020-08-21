package com.muban.common.router

class RouterPaths {
    companion object {
        //例子
        //登录模块
        private const val GROUP_LOGIN = "login"

        //练习模块
        private const val GROUP_DEMO = "demo"

        //登录页
        const val LOGIN_ACTIVITY = "/${GROUP_LOGIN}/LOGIN_ACTIVITY"

        //练习一
        const val DEMO_ONE_ACTIVITY = "/${GROUP_DEMO}/DEMO_ONE_ACTIVITY"

        //练习二
        const val DEMO_TWO_ACTIVITY = "/${GROUP_DEMO}/DEMO_TWO_ACTIVITY"
    }
}