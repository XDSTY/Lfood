package com.xdsty.userservice.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;

/**
 * md5工具类
 *
 * @author 张富华
 * @date 2020/6/16 15:59
 */
public class MD5Util {

    public static String md5Hex(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes(Charset.forName("UTF-8")));
    }

}
