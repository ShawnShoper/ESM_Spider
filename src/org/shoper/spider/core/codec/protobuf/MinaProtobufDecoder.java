package org.shoper.spider.core.codec.protobuf;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MinaProtobufDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {

		// ���û�н�����Header���֣�4�ֽڣ���ֱ�ӷ���false
		if (in.remaining() < 4) {
			return false;
		} else {

			// ��ǿ�ʼλ�ã����һ����Ϣû��������򷵻ص����λ��
			in.mark();

			// ��ȡheader���֣���ȡbody����
			int bodyLength = in.getInt();

			// ���bodyû�н���������ֱ�ӷ���false
			if (in.remaining() < bodyLength) {
				in.reset(); // IoBuffer position�ص�ԭ����ǵĵط�
				return false;
			} else {
				byte[] bodyBytes = new byte[bodyLength];
				in.get(bodyBytes); // ��ȡbody����
				//StudentMsg.Student student = StudentMsg.Student.parseFrom(bodyBytes); // ��body��protobuf�ֽ���ת��Student����
				//out.write(student); // ������һ����Ϣ
				return true;
			}
		}
	}
}