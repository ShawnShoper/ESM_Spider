package org.shoper.spider.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.shoper.spider.core.codec.CoderFactory;
import org.shoper.spider.core.handler.Handler;

public class SpiderServices {
	private static int PORT;
	private static int MINREADBUFFERSIZE;
	private static int MAXREADBUFFERSIZE;
	private static int SENDBUFFERSIZE;
	private static boolean ISORDER;
	private static int COREPOOLSIZE;
	private static int MAXIMUMPOOLSIZE;
	private static int IDLETIME;
	private static Logger logger = Logger.getLogger(SpiderServices.class);

	public static void main(String[] args) throws Exception {
		try {
			logger.info("server starting......");
			NioSocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime()
					.availableProcessors());
			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setSendBufferSize(SENDBUFFERSIZE);
			acceptor.getSessionConfig().setMinReadBufferSize(MINREADBUFFERSIZE);
			acceptor.getSessionConfig().setMaxReadBufferSize(MAXREADBUFFERSIZE);
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CoderFactory()));
			acceptor.setHandler(new Handler());
			if(ISORDER)
				acceptor.getFilterChain().addLast("threadpool", new ExecutorFilter(new OrderedThreadPoolExecutor(COREPOOLSIZE, MAXIMUMPOOLSIZE)));
			else
				acceptor.getFilterChain().addLast("threadpool", new ExecutorFilter(Executors.newCachedThreadPool()));
			if(IDLETIME>0)
				acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDLETIME);
			acceptor.bind(new InetSocketAddress(PORT));
			logger.info("server started on the port:" + PORT);
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
	}

	static {
		Properties properties = new Properties();
		try {
			properties.load(SpiderServices.class
					.getResourceAsStream("/server.properties"));
			PORT = Integer.valueOf((String) properties.get("port"));
			MINREADBUFFERSIZE = Integer.valueOf((String)properties.getProperty("minReadBufferSize"));
			MAXREADBUFFERSIZE = Integer.valueOf((String)properties.getProperty("maxReadBufferSize"));
			SENDBUFFERSIZE = Integer.valueOf((String)properties.getProperty("sendBufferSize"));
			COREPOOLSIZE = Integer.valueOf((String)properties.getProperty("corePoolSize"));
			MAXIMUMPOOLSIZE = Integer.valueOf((String)properties.getProperty("maximumPoolSize"));
			IDLETIME = Integer.valueOf((String)properties.getProperty("idleTime"));
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
	}
}

