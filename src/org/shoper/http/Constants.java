package org.shoper.http;

public class Constants {
	
	/**
	 * ���������ҳ���ȣ��ֽڣ�
	 */
	public static final int MAX_HTML_LENGTH = 1048576;//1MB
	
	/**
	 * URL���˻���ռ���ڴ��С
	 * ֵԽС������Խ��
	 */
	public static final int URL_FILTER_CACHE_SIZE = 134217728;//16M
	
	/**
	 * URl���˻���洢�������
	 * ֵԽ�������Խ��
	 */
	public static final int URL_FILTER_CACHE_MAX_LINE = 1000*10000;
	
	/**
	 * ץȡ�����߳���
	 */
	public static final int URL_THREAD_NUMBER = 3;
	
	/**
	 * ץȡ������ʶ
	 * @author LiuShangYang
	 */
	public static enum C{START,STOP,RESTART}
	
	
}
