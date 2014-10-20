package org.shoper.http.exception;


/**
 * �����쳣
 * �����������Ƴ���,��黷�������ݿ����ӣ��������ӵȣ�
 * @author LiuShangYang
 *
 */
public class FunctionException extends Exception {
	private static final long serialVersionUID = 1L;

	public FunctionException(){}
	
	public FunctionException(Throwable cause){
		super(cause);
	}
	
	public FunctionException(String message) {
		super(message);
	}
	
	
	/**
	 * ��¼���Ӵ���
	 * @param urlId
	 * @author LiuShangYang 2014��6��26��
	 */
	public void recordUrl(String urlId){
	}
	
	
}
