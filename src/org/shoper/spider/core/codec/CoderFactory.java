package org.shoper.spider.core.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class CoderFactory implements ProtocolCodecFactory{
	private CumulativeProtocolDecoder decoder;
	private ProtocolEncoder encoder;
	
	public CoderFactory(CumulativeProtocolDecoder decoder,
			ProtocolEncoder encoder) {
		this.decoder = decoder;
		this.encoder = encoder;
	}

	public CoderFactory() {
		this.decoder = new DefaultDecoder();
		this.encoder = new DefaultEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession io) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession io) throws Exception {
		return encoder;
	}

}
