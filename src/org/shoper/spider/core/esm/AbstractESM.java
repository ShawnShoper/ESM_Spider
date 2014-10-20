package org.shoper.spider.core.esm;

public abstract class AbstractESM{
	public abstract void process()throws Exception;
	public abstract void destroy();
	public abstract void init()throws Exception;
}

