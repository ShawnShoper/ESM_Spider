package org.shoper.spider.core.codec.protobuf;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MinaProtobufEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {

    	//StudentMsg.Student student = (StudentMsg.Student) message;
      //  byte[] bytes = student.toByteArray(); // Student����תΪprotobuf�ֽ���
        /*int length = bytes.length;
        
        IoBuffer buffer = IoBuffer.allocate(length + 4);
        buffer.putInt(length); // write header
        buffer.put(bytes); // write body
        buffer.flip();
        out.write(buffer);*/
    }
}