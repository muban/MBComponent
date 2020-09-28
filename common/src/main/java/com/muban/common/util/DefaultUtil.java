package com.muban.common.util;

import android.text.TextUtils;

/**
 * 默认值工具类
 * Created by PC-msi on 2018/3/29.
 */

public class DefaultUtil {
    private DefaultUtil() {
    }

    public static String getString(String value, String defaultValue) {
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }

    public static String getString(String value) {
        return TextUtils.isEmpty(value) ? getString(value, "——") : value;
    }

    /**
     * 获取指定长度文本
     *
     * @param content 文本内容
     * @param length  最大长度
     * @return 截取文本
     */
    public static String getMaxLengthText(String content, int length) {
        if (content == null) return "";
        return content.length() > length
                ? content.substring(0, length) + "..."
                : content;
    }

    /**
     * 获取中间省略的指定长度文本
     *
     * @param content 文本内容
     * @param length  最大长度
     * @return 截取文本
     */
    public static String getCenterMaxLengthText(String content, int length) {
        if (content == null) return "";
        return content.length() > length
                ? content.substring(0, length / 2) + "..."
                + content.substring(content.length() - length / 2)
                : content;
    }

    /**
     * 获取中间省略的指定长度文本
     *
     * @param content 文本内容
     * @param length  最大长度
     * @param replace 替换内容
     * @return 替换文本
     */
    public static String getMaxLengthReplaceText(String content, int length, String replace) {
        if (content == null) return "";
        return content.length() > length
                ? replace
                : content;
    }
}
