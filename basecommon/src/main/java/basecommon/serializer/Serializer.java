package basecommon.serializer;

/**
 * @author 张富华
 * @date 2020/8/6 17:07
 */
public interface Serializer {
    /**
     * Encode object to byte[].
     *
     * @param <T> the type parameter
     * @param t   the t
     * @return the byte [ ]
     */
    <T> byte[] serialize(T t);

    /**
     * Decode t from byte[].
     *
     * @param <T>   the type parameter
     * @param bytes the bytes
     * @return the t
     */
    <T> T deserialize(byte[] bytes);
}
