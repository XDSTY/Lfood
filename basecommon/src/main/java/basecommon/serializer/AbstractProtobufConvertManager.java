package basecommon.serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author 张富华
 * @date 2020/8/6 17:29
 */
public abstract class AbstractProtobufConvertManager {

    private static Map<String, String> parseClassMap = new ConcurrentHashMap<>();

    public static String fetchParseFormClass(String className) {
        return parseClassMap.get(className);
    }

    public abstract void initProtobufMapInfo();

    protected void putParseClassMap(String className, String parseClassName) {
        parseClassMap.put(className, parseClassName);
    }
}
