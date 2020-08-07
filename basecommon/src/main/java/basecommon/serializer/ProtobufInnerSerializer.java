package basecommon.serializer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author 张富华
 * @date 2020/8/6 17:32
 */
public class ProtobufInnerSerializer {
    private static final ProtobufHelper PROTOBUF_HELPER = new ProtobufHelper();

    /**
     * Encode method name
     */
    private static final String METHOD_TOBYTEARRAY = "toByteArray";
    /**
     * Decode method name
     */
    private static final String METHOD_PARSEFROM = "parseFrom";

    public static byte[] serializeContent(Object request) {

        Class clazz = request.getClass();
        Method method = PROTOBUF_HELPER.toByteArrayMethodMap.get(clazz);
        if (method == null) {
            try {
                method = clazz.getMethod(METHOD_TOBYTEARRAY);
                method.setAccessible(true);
                PROTOBUF_HELPER.toByteArrayMethodMap.put(clazz, method);
            } catch (Exception e) {
                throw new RuntimeException("Cannot found method " + clazz.getName()
                        + ".toByteArray(), please check the generated code.", e);
            }
        }
        byte[] bytes = new byte[0];
        try {
            bytes = (byte[])method.invoke(request);
        } catch (Exception e) {
            throw new RuntimeException("serialize occurs exception", e);
        }

        return bytes;
    }

    public static <T> T deserializeContent(String responseClazz, byte[] content) {
        if (content == null || content.length == 0) {
            return null;
        }
        String targetParseFormClassName = AbstractProtobufConvertManager.fetchParseFormClass(responseClazz);
        Class clazz = PROTOBUF_HELPER.getPbClass(targetParseFormClassName);

        Method method = PROTOBUF_HELPER.parseFromMethodMap.get(clazz);
        if (method == null) {
            try {
                method = clazz.getMethod(METHOD_PARSEFROM, byte[].class);
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("Cannot found static method " + clazz.getName()
                            + ".parseFrom(byte[]), please check the generated code");
                }
                method.setAccessible(true);
                PROTOBUF_HELPER.parseFromMethodMap.put(clazz, method);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot found method " + clazz.getName()
                        + ".parseFrom(byte[]), please check the generated code", e);
            }
        }
        Object result;
        try {
            result = method.invoke(null, content);
        } catch (Exception e) {
            throw new RuntimeException("Error when invoke " + clazz.getName() + ".parseFrom(byte[]).", e);
        }

        return (T)result;
    }
}
