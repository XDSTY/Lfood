package basecommon.serializer;

import com.google.protobuf.GeneratedMessageV3;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 张富华
 * @date 2020/8/6 17:08
 */
public class ProtobufSerializer implements Serializer {

    protected static final Charset UTF8 = StandardCharsets.UTF_8;

    @Override
    public <T> byte[] serialize(T t) {
        if (t == null) {
            throw new NullPointerException();
        }

        //for cross language,write FullName to data,which defines in proto file
        GeneratedMessageV3 newBody = (GeneratedMessageV3) t;
        byte[] body = ProtobufInnerSerializer.serializeContent(newBody);
        final String name = newBody.getDescriptorForType().getName();
        final byte[] nameBytes = name.getBytes(UTF8);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + nameBytes.length + body.length);
        byteBuffer.putInt(nameBytes.length);
        byteBuffer.put(nameBytes);
        byteBuffer.put(body);
        byteBuffer.flip();
        byte[] content = new byte[byteBuffer.limit()];
        byteBuffer.get(content);
        return content;
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException();
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int clazzNameLength = byteBuffer.getInt();
        byte[] clazzName = new byte[clazzNameLength];
        byteBuffer.get(clazzName);
        byte[] body = new byte[bytes.length - clazzNameLength - 4];
        byteBuffer.get(body);
        final String descriptorName = new String(clazzName, UTF8);
        Object protobufObject = ProtobufInnerSerializer.deserializeContent(descriptorName, body);
        return (T) protobufObject;
    }


    private static class InnerInstance {
        static ProtobufSerializer protobufSerializer = new ProtobufSerializer();

        static ProtobufSerializer getProtobufSerializer() {
            return protobufSerializer;
        }
    }

    public static <T> byte[] serializeToByteArray(T t) {
        return InnerInstance.getProtobufSerializer().serialize(t);
    }

    public static <T> T deserializeBytes(byte[] bytes) {
        return InnerInstance.getProtobufSerializer().deserialize(bytes);
    }

}
