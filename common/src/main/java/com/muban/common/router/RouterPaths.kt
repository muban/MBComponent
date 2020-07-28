package com.muban.common.router

class RouterPaths {
    companion object {
        //例子
        //登录模块
        private const val GROUP_LOGIN = "login"

        //支付模块
        private const val GROUP_PAY = "pay"

        //登录页
        const val LOGIN_ACTIVITY = "/${GROUP_LOGIN}/LOGIN_ACTIVITY"

        //注册页
        const val REGISTERED_ACTIVITY = "/${GROUP_LOGIN}/REGISTERED_ACTIVITY"

        //微信
        const val WECHAT_ACTIVITY = "/${GROUP_PAY}/WECHAT_ACTIVITY"

        //支付宝
        const val ALI_ACTIVITY = "/${GROUP_PAY}/ALI_ACTIVITY"
    }
}