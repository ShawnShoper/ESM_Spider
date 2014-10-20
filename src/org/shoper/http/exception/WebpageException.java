package org.shoper.http.exception;


/**
 * ҳ���������ҳ�治����404��
 * ��֪�޷�ץȡ��ҳ�棬����ѭ���ض���
 * ���������������»�ȡ
 * @author LiuShangYang
 *
 */
public class WebpageException extends Exception {
	
	public WebpageException(int statusCode){
		super();
		this.StatusCode = statusCode;
	}
	
	public WebpageException(int statusCode,Throwable cause){
		super(cause);
		this.StatusCode = statusCode;
	}
	
	public WebpageException(int statusCode,String message) {
		super(message);
		this.StatusCode = statusCode;
	}
	
	/**
	 * ��¼���Ӵ���
	 * @param urlId
	 * @author LiuShangYang 2014��6��26��
	 */
	public void recordUrl(String urlId){
	}
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ״̬��(����0��������)
	 */
	public int StatusCode = 0;
	
	/**
	 *  �Զ���״̬��1���ļ����Ͳ�������
	 */
	public static final int HTTP_FILE = 1;
	
	/**
	 *  �Զ���״̬��2���ļ����ȳ����涨
	 */
	public static final int HTTP_SIZE = 2;
	
	/**
	 *  �Զ���״̬��3���ض�����ѭ��
	 */
	public static final int HTTP_REDIRECT = 3;

}
