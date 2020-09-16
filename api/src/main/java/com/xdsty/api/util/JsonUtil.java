package com.xdsty.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.xdsty.api.controller.content.order.OrderModuleContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/9/16 10:46
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

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("转化为json失败，obj: {}", obj);
        }
        return null;
    }

    /**
     * 数组类型转化
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArrJson(String json, Class<T> clazz) {
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        try {
            return mapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            logger.error("json转化失败，class: {}, json: {}", clazz.getSimpleName(), json);
        }
        return null;
    }
}
