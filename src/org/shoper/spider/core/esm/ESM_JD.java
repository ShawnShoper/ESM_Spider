package org.shoper.spider.core.esm;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ESM_JD extends AbstractESM {
	private Logger logger = Logger.getLogger(ESM_JD.class);
	private static String rootUrl;

	@Override
	public void process() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {
		logger.info("≥ı ºªØ"+ESM_JD.class.getName()+".....");
		try {
			Properties properties = new Properties();
			properties.load(ESM_JD.class.getResourceAsStream("/jd.properties"));
			rootUrl = properties.getProperty("rootUrl");
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}
