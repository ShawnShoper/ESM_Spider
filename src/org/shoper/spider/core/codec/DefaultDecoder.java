package org.shoper.spider.core.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class DefaultDecoder extends CumulativeProtocolDecoder{
	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer arg1,
			ProtocolDecoderOutput arg2) throws Exception {
		return false;
	}

}
