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

        //translate to pb
        final PbConvertor pbConvertor = AbstractProtobufConvertManager.fetchConvertor(
                t.getClass().getName());
        //for cross language,write FullName to data,which defines in proto file
        GeneratedMessageV3 newBody = (GeneratedMessageV3)pbConvertor.convert2Proto(t);
        byte[] body = ProtobufInnerSerializer.serializeContent(newBody);
        final String name = newBody.getDescriptorForType().getFullName();
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
        Class protobufClazz = AbstractProtobufConvertManager.fetchProtoClass(descriptorName);
        Object protobufObject = ProtobufInnerSerializer.deserializeContent(protobufClazz.getName(), body);
        //translate back to core model
        final PbConvertor pbConvertor = AbstractProtobufConvertManager.fetchReversedConvertor(protobufClazz.getName());
        Object newBody = pbConvertor.convert2Model(protobufObject);
        return (T)newBody;
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
