package com.xdsty.api.util;

import org.springframework.util.Base64Utils;
import java.nio.charset.StandardCharsets;

/**
 * @author 张富华
 * @date 2020/6/16 10:22
 */
public final class Base64Util {

    /**
     * base64 解码
     *
     * @param base64Str base64字符串
     * @return 解码后的字符串
     */
    public static String decode(String base64Str) {
        return new String(Base64Utils.decodeFromString(base64Str), StandardCharsets.UTF_8);
    }

    /**
     * base64编码
     *
     * @param str
     * @return 编码后的字符串
     */
    public static String encode(String str) {
        return Base64Utils.encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

}