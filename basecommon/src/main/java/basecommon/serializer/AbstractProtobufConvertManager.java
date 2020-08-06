package basecommon.serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author 张富华
 * @date 2020/8/6 17:29
 */
public abstract class AbstractProtobufConvertManager {

    private static Map<String, PbConvertor> convertorMap = new ConcurrentHashMap<>();

    private static Map<String, PbConvertor> reverseConvertorMap = new ConcurrentHashMap<>();

    private static Map<String, Class> protoClazzMap = new ConcurrentHashMap<>();

    public static PbConvertor fetchConvertor(String clazz) {
        return convertorMap.get(clazz);
    }

    public static PbConvertor fetchReversedConvertor(String clazz) {
        return reverseConvertorMap.get(clazz);
    }

    public static Class fetchProtoClass(String clazz) {
        return protoClazzMap.get(clazz);
    }

    public abstract void initProtobufMapInfo();
}
