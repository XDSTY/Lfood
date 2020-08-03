package com.xdsty.userservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张富华
 * @date 2020/7/22 11:01
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
    }

    /**
     * 解析json
     *
     * @param json  json
     * @param clazz 解析后的对象类型
     * @param <T>   对象泛型
     * @return 解析后的对象
     */
    public static <T> T parseJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error("json转化失败，class: {}, json: {}", clazz.getSimpleName(), json);
        }
        return null;
    }

}
