package com.xdsty.userservice.config.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.common.serialize.Constants;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author 张富华
 * @date 2020/8/24 14:55
 */
@SPI
public class ProtobufSerialization implements Serialization {

    @Override
    public byte getContentTypeId() {
        return Constants.PROTOBUF_SERIALIZATION_ID;
    }

    @Override
    public String getContentType() {
        return "x-application/protobuf";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return null;
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream input) throws IOException {
        return null;
    }

}
