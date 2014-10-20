package org.shoper.http;

public class Constants {
	
	/**
	 * 限制最大网页长度（字节）
	 */
	public static final int MAX_HTML_LENGTH = 1048576;//1MB
	
	/**
	 * URL过滤缓存占用内存大小
	 * 值越小错误率越高
	 */
	public static final int URL_FILTER_CACHE_SIZE = 134217728;//16M
	
	/**
	 * URl过滤缓存存储最多数据
	 * 值越大错误率越高
	 */
	public static final int URL_FILTER_CACHE_MAX_LINE = 1000*10000;
	
	/**
	 * 抓取链接线程数
	 */
	public static final int URL_THREAD_NUMBER = 3;
	
	/**
	 * 抓取操作标识
	 * @author LiuShangYang
	 */
	public static enum C{START,STOP,RESTART}
	
	
}
